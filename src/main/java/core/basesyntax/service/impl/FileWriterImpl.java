package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String data, String filePath) {
        if (filePath == null) {
            throw new RuntimeException("File path cannot be null");
        }
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new RuntimeException("File does not exist: " + filePath);
        }
        try {
            Files.write(path, data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write file" + filePath, e);
        }
    }
}
