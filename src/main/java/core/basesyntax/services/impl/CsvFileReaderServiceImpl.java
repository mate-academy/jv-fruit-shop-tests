package core.basesyntax.services.impl;

import core.basesyntax.exceptions.NoSuchFileEx;
import core.basesyntax.services.CsvFileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvFileReaderServiceImpl implements CsvFileReaderService {
    @Override
    public List<String> readFromFile(Path filePath) {
        try {
            return Files.readAllLines(filePath);
        } catch (IOException | NullPointerException e) {
            throw new NoSuchFileEx("Can't read data from file: "
                    + filePath, e);
        }
    }
}
