package core.basesyntax.service.impl;

import core.basesyntax.exception.FileReaderException;
import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    @Override
    public List<String> readFromFile(String filePath) {
        try {
            var data = Files.readAllLines(Path.of(filePath));

            if (!containsNonEmptyLine(data)) {
                throw new RuntimeException("You think i'm stupid?...");
            }
            return data;
        } catch (IOException e) {
            throw new FileReaderException("Error reading from a file: " + filePath, e);
        }
    }

    private static boolean containsNonEmptyLine(List<String> data) {
        return data.stream().anyMatch(line -> !line.trim().isEmpty());
    }
}
