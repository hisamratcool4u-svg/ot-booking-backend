package com.otapp.controller;

import com.otapp.entity.Patient;
import com.otapp.repository.PatientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientRepository repo;

    public PatientController(PatientRepository repo) {
        this.repo = repo;
    }

    // 🔹 Get all patients
    @GetMapping
    public List<Patient> getAllPatients() {
        return repo.findAll();
    }

    // 🔹 Create a new patient record
    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return repo.save(patient);
    }
}
