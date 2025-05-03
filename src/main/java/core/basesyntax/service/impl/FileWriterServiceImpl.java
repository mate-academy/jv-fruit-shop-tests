package core.basesyntax.service.impl;

import core.basesyntax.service.interfaces.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeData(String data, String filePath) {
        if (filePath == null || data == null) {
            throw new IllegalArgumentException("Data or file path can't be null!");
        }
        Path path = Path.of(filePath);
        try {
            Files.writeString(path, data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to a file " + filePath, e);
        }
    }
}
