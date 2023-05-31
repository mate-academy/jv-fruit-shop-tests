package com.mate.fruitshop.service.impl;

import com.mate.fruitshop.service.WriterService;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriterServiceImpl implements WriterService {
    @Override
    public void write(String report, String reportFileDir) {
        Path reportFile = Paths.get(reportFileDir);
        try (BufferedWriter writer = Files.newBufferedWriter(reportFile)) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write report to:"
                    + reportFile, e);
        }
    }
}
