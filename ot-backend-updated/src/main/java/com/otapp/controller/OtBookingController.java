package com.otapp.controller;

import com.otapp.entity.BookingStatus;
import com.otapp.entity.OtBooking;
import com.otapp.service.OtBookingService;
import com.otapp.repository.OtBookingRepository;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class OtBookingController {
    private final OtBookingService service;
    private final OtBookingRepository repo;
    public OtBookingController(OtBookingService service, OtBookingRepository repo){this.service = service; this.repo = repo;}

    @PostMapping
    public OtBooking create(@RequestBody OtBooking booking){ return service.create(booking); }

    @GetMapping
    public List<OtBooking> all(){ return repo.findAll(); }

    @PatchMapping("/{id}/status")
    public OtBooking updateStatus(@PathVariable Long id, @RequestParam BookingStatus status){
        return service.changeStatus(id, status);
    }

    @PutMapping("/{id}")
    public OtBooking reschedule(@PathVariable Long id, @RequestBody java.util.Map<String,String> body){
        try{
            OffsetDateTime start = OffsetDateTime.parse(body.get("scheduledStart"));
            OffsetDateTime end = OffsetDateTime.parse(body.get("scheduledEnd"));
            return service.reschedule(id, start, end);
        }catch(DateTimeParseException e){
            throw new com.otapp.service.ValidationException("Invalid datetime format. Use ISO-8601.");
        }
    }
}
