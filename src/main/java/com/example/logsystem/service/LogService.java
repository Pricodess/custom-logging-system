package com.example.logsystem.service;

import com.example.logsystem.model.LogEntry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LogService {

    private static final Set<String> ALLOWED_LEVELS = Set.of("INFO", "WARN", "ERROR");
    private static final String FILE_PATH = "logs/logs.txt";

    private final List<LogEntry> logList = new ArrayList<>();

    public void addLog(String level, String message) {
        validateLevel(level);

        LogEntry log = new LogEntry(level, message);
        logList.add(log);

        writeLogToFile(log);
    }

    public List<LogEntry> getLogs(String level) {

        if (level == null || level.isBlank()) {
            return logList;
        }

        validateLevel(level);

        return logList.stream()
                .filter(log -> log.getLevel().equals(level))
                .toList();
    }

    private void validateLevel(String level) {
        if (!ALLOWED_LEVELS.contains(level)) {
            throw new IllegalArgumentException(
                    "Invalid log level. Allowed values: INFO, WARN, ERROR"
            );
        }
    }

    private void writeLogToFile(LogEntry log) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(log.toString());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write log to file", e);
        }
    }

    @PostConstruct
    public void loadLogsFromFile() {

        File file = new File(FILE_PATH);

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile(); // create empty file if missing
            } catch (IOException e) {
                throw new RuntimeException("Failed to create log file", e);
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {
                logList.add(LogEntry.fromString(line));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read logs from file", e);
        }
    }

}
