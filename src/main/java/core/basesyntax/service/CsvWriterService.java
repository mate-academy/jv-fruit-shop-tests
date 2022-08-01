package core.basesyntax.service;

import java.nio.file.Path;

public interface CsvWriterService {
    void write(Path filePath, String report);
}
