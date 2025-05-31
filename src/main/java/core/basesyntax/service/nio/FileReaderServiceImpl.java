package core.basesyntax.service.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {

    @Override
    public List<String> read(String path) {
        try {
            Path filePath = Path.of(path);
            if (!Files.exists(filePath)) {
                throw new RuntimeException("File does not exist at path: " + path);
            }
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file at: " + path + ". " + e
                    .getMessage(), e);
        }
    }
}
