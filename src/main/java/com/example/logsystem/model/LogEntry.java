package com.example.logsystem.model;

import java.time.LocalDateTime;

public class LogEntry {

    private final String level;
    private final String message;
    private LocalDateTime timestamp;

    public LogEntry(String level, String message) {
        this.level = level;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static LogEntry fromString(String line) {
        String[] parts = line.split("\\|");
        LogEntry log = new LogEntry(parts[1], parts[2]);
        log.timestamp = LocalDateTime.parse(parts[0]);
        return log;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return  timestamp + "|" + level + "|" + message;
    }
}
