package com.otapp.service;

import java.util.List;

public class ConflictException extends RuntimeException {
    private List<?> conflicts;
    public ConflictException(String msg){ super(msg); }
    public ConflictException(String msg, List<?> conflicts){ super(msg); this.conflicts = conflicts; }
    public List<?> getConflicts(){ return this.conflicts; }
}
