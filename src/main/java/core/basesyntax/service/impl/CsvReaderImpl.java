package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvReaderImpl implements FileReaderService {

    @Override
    public List<String> parse(String filePath) {
        if (filePath == null) {
            throw new RuntimeException("filePath parameter is null");
        }
        try {
            if (!Files.readAllLines(Path.of(filePath)).isEmpty()) {
                return Files.readAllLines(Path.of(filePath));
            }
            throw new RuntimeException("file: " + filePath + " is empty");
        } catch (IOException e) {
            throw new RuntimeException("cant read from file: " + filePath);
        }
    }
}
