package core.basesyntax.services;

import java.nio.file.Path;
import java.util.List;

public interface CsvFileWriterService {
    void writeToCsvFile(List<String> data, Path filePath);
}
