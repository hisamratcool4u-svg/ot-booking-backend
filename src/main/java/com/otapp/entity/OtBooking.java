package com.otapp.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class OtBooking {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne private OperationTheatre ot;
    @ManyToOne private Patient patient;

    private OffsetDateTime scheduledStart;
    private OffsetDateTime scheduledEnd;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private String reason;

    @Version
    private Long version;

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public OperationTheatre getOt(){return ot;}
    public void setOt(OperationTheatre ot){this.ot=ot;}
    public Patient getPatient(){return patient;}
    public void setPatient(Patient patient){this.patient=patient;}
    public OffsetDateTime getScheduledStart(){return scheduledStart;}
    public void setScheduledStart(OffsetDateTime scheduledStart){this.scheduledStart=scheduledStart;}
    public OffsetDateTime getScheduledEnd(){return scheduledEnd;}
    public void setScheduledEnd(OffsetDateTime scheduledEnd){this.scheduledEnd=scheduledEnd;}
    public BookingStatus getStatus(){return status;}
    public void setStatus(BookingStatus status){this.status=status;}
    public String getReason(){return reason;}
    public void setReason(String reason){this.reason=reason;}
    public Long getVersion(){return version;}
    public void setVersion(Long version){this.version=version;}
}
