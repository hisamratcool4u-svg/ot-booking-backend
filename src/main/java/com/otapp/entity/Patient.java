package com.otapp.entity;

import jakarta.persistence.*;

@Entity
public class Patient {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String phone;
    private String medicalRecordNo;

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone=phone;}
    public String getMedicalRecordNo(){return medicalRecordNo;}
    public void setMedicalRecordNo(String medicalRecordNo){this.medicalRecordNo=medicalRecordNo;}
}
