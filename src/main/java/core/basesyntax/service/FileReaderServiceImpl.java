package core.basesyntax.service;

import core.basesyntax.service.interfaces.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public List<String> readFromFile(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException exception) {
            throw new RuntimeException("File does not exist!", exception);
        }
    }
}
