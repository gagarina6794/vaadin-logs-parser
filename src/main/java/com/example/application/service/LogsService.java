package com.example.application.service;

import com.example.application.model.LogItem;
import com.example.application.repository.LogsRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class LogsService {
    private final LogsRepository logsRepository;

    public LogsService(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    public void saveLog(@NotNull LogItem item) {
        logsRepository.save(item);
    }

    public List<LogItem> getAll() {
        return logsRepository.findAll();
    }

    public void removeAll() {
        logsRepository.deleteAll();
    }
}
