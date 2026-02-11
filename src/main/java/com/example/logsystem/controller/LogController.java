package com.example.logsystem.controller;

import com.example.logsystem.model.LogEntry;
import com.example.logsystem.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public ResponseEntity<String> addLog(@RequestBody LogEntry request) {
        logService.addLog(request.getLevel(), request.getMessage());
        return ResponseEntity.ok("Log added successfully");
    }


    @GetMapping
    public ResponseEntity<List<LogEntry>> getLogs(@RequestParam(required = false) String level) {
        return ResponseEntity.ok(logService.getLogs(level));
    }
}
