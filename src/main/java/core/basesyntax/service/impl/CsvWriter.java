package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter implements Writer {
    @Override
    public void write(String path, String data) {
        validate(path, data);
        try (FileWriter fileWriter = new FileWriter(path.trim())) {
            fileWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file: " + path);
        }
    }

    private void validate(String path, String data) {
        if (path == null) {
            throw new RuntimeException("Path must not be null.");
        }
        if (data == null) {
            throw new RuntimeException("Data must not be null.");
        }

        if (data.isBlank() || data.isEmpty()) {
            throw new RuntimeException("Data must not be empty.");
        }
    }
}
