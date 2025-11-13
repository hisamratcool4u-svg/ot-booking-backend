package com.otapp.controller;

import com.otapp.entity.BookingStatus;
import com.otapp.entity.OtBooking;
import com.otapp.repository.OtBookingRepository;
import com.otapp.service.OtBookingService;
import com.otapp.service.ValidationException;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class OtBookingController {

    private final OtBookingService service;
    private final OtBookingRepository repo;

    public OtBookingController(OtBookingService service, OtBookingRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @PostMapping
    public OtBooking create(@RequestBody OtBooking booking) {
        return service.create(booking);
    }

    @GetMapping
    public List<OtBooking> all() {
        return repo.findAll();
    }

    @PatchMapping("/{id}/status")
    public OtBooking updateStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        return service.changeStatus(id, status);
    }

    @PutMapping("/{id}")
    public OtBooking reschedule(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String startStr = body.get("scheduledStart");
            String endStr = body.get("scheduledEnd");

            if (startStr == null || endStr == null) {
                throw new ValidationException("Missing scheduledStart or scheduledEnd");
            }

            OffsetDateTime start = parseFlexibleDateTime(startStr);
            OffsetDateTime end = parseFlexibleDateTime(endStr);

            return service.reschedule(id, start, end);

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ValidationException("Invalid datetime format. Use ISO-8601 or provide offset (+05:30).");
        }
    }

    /**
     * Parse date-time string flexibly:
     * - Accepts ISO with 'Z' (UTC)
     * - Accepts with '+05:30' offset
     * - Accepts plain LocalDateTime (adds +05:30 offset)
     */
    private OffsetDateTime parseFlexibleDateTime(String input) {
        try {
            // Case 1: Has timezone info
            if (input.endsWith("Z") || input.contains("+") || input.contains("-")) {
                return OffsetDateTime.parse(input, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            }

            // Case 2: Plain datetime (add India timezone)
            LocalDateTime local = LocalDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return local.atOffset(ZoneOffset.ofHoursMinutes(5, 30));

        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid datetime: " + input);
        }
    }
}
