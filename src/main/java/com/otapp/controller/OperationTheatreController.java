package com.otapp.controller;

import com.otapp.entity.OperationTheatre;
import com.otapp.repository.OperationTheatreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ots")
@CrossOrigin(origins = "*")
public class OperationTheatreController {

    private final OperationTheatreRepository repo;

    public OperationTheatreController(OperationTheatreRepository repo) {
        this.repo = repo;
    }

    // 🔹 Get all Operation Theatres
    @GetMapping
    public List<OperationTheatre> getAllOperationTheatres() {
        return repo.findAll();
    }

    // 🔹 Create a new Operation Theatre
    @PostMapping
    public OperationTheatre createOperationTheatre(@RequestBody OperationTheatre ot) {
        return repo.save(ot);
    }
}
