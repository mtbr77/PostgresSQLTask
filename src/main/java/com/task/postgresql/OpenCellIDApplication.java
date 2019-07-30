package com.task.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class OpenCellIDApplication {
    private static ProcessingService processingService;

    @Autowired
    public void setProcessingService(ProcessingService processingService) {
        OpenCellIDApplication.processingService = processingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OpenCellIDApplication.class, args);
        processingService.start();
    }
}
