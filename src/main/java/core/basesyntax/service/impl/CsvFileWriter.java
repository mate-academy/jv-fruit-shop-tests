package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class CsvFileWriter implements FileWriter {
    @Override
    public void write(String data, String fileName) {
        Objects.requireNonNull(data, "data is null");
        Objects.requireNonNull(fileName, "file name is null");
        try {
            Files.writeString(Path.of(fileName), data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + fileName, e);
        }
    }
}
