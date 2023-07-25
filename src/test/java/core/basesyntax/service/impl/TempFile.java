package core.basesyntax.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TempFile {
    public File createTempInputFile() throws IOException {
        File tempFile = File.createTempFile("input", ".csv");
        Path resourcesDir = Path.of("src", "test", "resources");
        File inputFile = new File(resourcesDir.toFile(), tempFile.getName());
        if (!inputFile.exists()) {
            Files.copy(tempFile.toPath(), inputFile.toPath());
        }
        return inputFile;
    }

    public File createTempReportFile() throws IOException {
        File tempFile = File.createTempFile("report", ".csv");
        Path resourcesDir = Path.of("src", "test", "resources");
        File reportFile = new File(resourcesDir.toFile(), tempFile.getName());
        if (!reportFile.exists()) {
            Files.copy(tempFile.toPath(), reportFile.toPath());
        }
        return reportFile;
    }
}