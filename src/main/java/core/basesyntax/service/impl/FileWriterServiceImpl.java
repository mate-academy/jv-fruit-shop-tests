package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeReportToFile(String report, String outputPath) {
        try {
            Files.writeString(Path.of(outputPath), report);
        } catch (IOException e) {
            throw new RuntimeException("Cannot reach file by " + outputPath, e);
        }
    }
}
