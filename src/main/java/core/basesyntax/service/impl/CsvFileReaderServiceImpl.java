package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileReaderService;
import core.basesyntax.service.exception.FileOperationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvFileReaderServiceImpl implements CsvFileReaderService {

    @Override
    public List<String> readFromFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileOperationException("Failed to read from file: " + filePath, e);
        }
    }
}
