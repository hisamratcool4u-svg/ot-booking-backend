package com.otapp.service;

import com.otapp.entity.BookingStatus;
import com.otapp.entity.OtBooking;
import com.otapp.repository.OtBookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OtBookingService {
    private final OtBookingRepository bookingRepo;

    public OtBookingService(OtBookingRepository bookingRepo){
        this.bookingRepo = bookingRepo;
    }

    @Transactional
    public OtBooking create(OtBooking booking){
        boolean conflict = bookingRepo.existsConflict(booking.getOt().getId(),
                booking.getScheduledStart(), booking.getScheduledEnd(), null);
        if(conflict) throw new ConflictException("Time slot conflict");
        booking.setStatus(BookingStatus.SCHEDULED);
        return bookingRepo.save(booking);
    }

    @Transactional
    public OtBooking changeStatus(Long id, BookingStatus newStatus){
        OtBooking b = bookingRepo.findById(id).orElseThrow(ResourceNotFound::new);
        if(!isTransitionValid(b.getStatus(), newStatus)) throw new ValidationException("Invalid transition");
        b.setStatus(newStatus);
        return bookingRepo.save(b);
    }

    @Transactional
    public OtBooking reschedule(Long id, OffsetDateTime start, OffsetDateTime end){
        OtBooking b = bookingRepo.findById(id).orElseThrow(ResourceNotFound::new);
        if(start.isAfter(end) || start.equals(end)) throw new ValidationException("Invalid times");
        List<OtBooking> conflicts = bookingRepo.findConflicts(b.getOt().getId(), start, end, id);
        if(!conflicts.isEmpty()){
            // prepare simple conflict info
            throw new ConflictException("Time slot overlaps with existing bookings", conflicts.stream().map(c -> Map.of(
                    "id", c.getId(),
                    "start", c.getScheduledStart().toString(),
                    "end", c.getScheduledEnd().toString()
            )).collect(Collectors.toList()));
        }
        b.setScheduledStart(start);
        b.setScheduledEnd(end);
        b.setStatus(BookingStatus.RESCHEDULED);
        return bookingRepo.save(b);
    }

    private boolean isTransitionValid(BookingStatus oldS, BookingStatus newS){
        return switch (oldS) {
            case SCHEDULED -> newS == BookingStatus.IN_PROGRESS || newS == BookingStatus.CANCELLED;
            case IN_PROGRESS -> newS == BookingStatus.COMPLETED;
            default -> false;
        };
    }
}
