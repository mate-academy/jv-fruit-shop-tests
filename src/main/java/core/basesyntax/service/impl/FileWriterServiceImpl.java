package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void write(String report, String reportFilePath) {
        try {
            Files.writeString(Path.of(reportFilePath), report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write the report", e);
        }
    }
}
