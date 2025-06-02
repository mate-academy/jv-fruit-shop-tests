package core.basesyntax.service.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void write(String path, List<String> lines) {
        try {
            Files.write(Path.of(path), lines);
        } catch (IOException e) {
            throw new RuntimeException("Write path is incorrect or not accessible."
                    + " Please check again. " + e.getMessage(), e);
        }
    }
}
