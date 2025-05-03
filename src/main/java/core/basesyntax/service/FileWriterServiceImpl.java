package core.basesyntax.service;

import core.basesyntax.service.interfaces.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String report, String path) {
        try {
            Files.writeString(Path.of(path), report);
        } catch (IOException exception) {
            throw new RuntimeException("Cant write Report to File", exception);
        }
    }
}
