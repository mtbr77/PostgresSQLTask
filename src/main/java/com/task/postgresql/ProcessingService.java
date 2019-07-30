package com.task.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessingService {
    @Autowired
    private CsvProcessingService scvService;

    public void start() {
        scvService.start();
    }
}
