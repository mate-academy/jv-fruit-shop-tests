package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class CsvFileWriter implements FileWriter {
    @Override
    public void write(String data, String filePath) {
        Objects.requireNonNull(data, "data is null");
        Objects.requireNonNull(filePath, "file path is null");
        try {
            Files.writeString(Path.of(filePath), data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file, path:  " + filePath, e);
        }
    }
}
