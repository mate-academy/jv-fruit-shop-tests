package core.basesyntax.service.impl;

import core.basesyntax.service.CsvReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvReaderServiceImpl implements CsvReaderService {
    public List<String> read(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + filePath, e);
        }
    }
}
