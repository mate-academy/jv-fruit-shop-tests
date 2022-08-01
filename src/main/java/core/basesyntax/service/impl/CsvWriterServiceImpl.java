package core.basesyntax.service.impl;

import core.basesyntax.service.CsvWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvWriterServiceImpl implements CsvWriterService {
    @Override
    public void write(Path filePath, String report) {
        try {
            Files.write(filePath,report.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + filePath, e);
        }
    }
}
