package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriterServiceImpl implements WriterService {
    @Override
    public void writeToFile(String finalReport, String filePath) {
        if (finalReport == null || finalReport.isBlank() || filePath == null
                || filePath.isBlank()) { //added after approval
            throw new RuntimeException("Can't write report: it's null or empty");
        }
        try {
            Files.writeString(Path.of(filePath), finalReport);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file:" + filePath, e);
        }
    }
}
