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
    public OperationTheatreController(OperationTheatreRepository repo){this.repo = repo;}

    @GetMapping
    public List<OperationTheatre> all(){ return repo.findAll(); }

    @PostMapping
    public OperationTheatre create(@RequestBody OperationTheatre ot){ return repo.save(ot); }
}
