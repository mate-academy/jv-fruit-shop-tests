package core.basesyntax.service;

import java.nio.file.Path;
import java.util.List;

public interface CsvReaderService {
    List<String> read(Path filePath);
}
