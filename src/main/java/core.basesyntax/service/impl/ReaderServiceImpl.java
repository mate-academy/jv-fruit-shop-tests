package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    @Override
    public List<String> readFromFile(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty.");
        }
        Path filePath = Path.of(path);
        if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
            throw new IllegalArgumentException("File cannot be read or not readable");
        }
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file: " + path, e);
        }
    }
}
