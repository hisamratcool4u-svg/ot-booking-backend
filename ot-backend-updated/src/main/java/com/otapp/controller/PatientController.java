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
    public PatientController(PatientRepository repo){this.repo = repo;}
    @GetMapping public List<Patient> all(){ return repo.findAll(); }
    @PostMapping public Patient create(@RequestBody Patient p){ return repo.save(p); }
}
