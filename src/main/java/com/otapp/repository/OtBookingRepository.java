package com.otapp.repository;

import com.otapp.entity.OtBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface OtBookingRepository extends JpaRepository<OtBooking, Long> {

    @Query(""" 
        SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
        FROM OtBooking b
        WHERE b.ot.id = :otId
          AND b.status IN ('SCHEDULED', 'IN_PROGRESS', 'RESCHEDULED')
          AND (:bookingId IS NULL OR b.id <> :bookingId)
          AND NOT (b.scheduledEnd <= :start OR b.scheduledStart >= :end)
    """)
    boolean existsConflict(@Param("otId") Long otId, @Param("start") OffsetDateTime start,
                           @Param("end") OffsetDateTime end, @Param("bookingId") Long bookingId);

    @Query(""" 
        SELECT b FROM OtBooking b
        WHERE b.ot.id = :otId
          AND b.status IN ('SCHEDULED', 'IN_PROGRESS', 'RESCHEDULED')
          AND b.id <> :bookingId
          AND NOT (b.scheduledEnd <= :start OR b.scheduledStart >= :end)
    """)
    List<OtBooking> findConflicts(@Param("otId") Long otId, @Param("start") OffsetDateTime start,
                                  @Param("end") OffsetDateTime end, @Param("bookingId") Long bookingId);
}
