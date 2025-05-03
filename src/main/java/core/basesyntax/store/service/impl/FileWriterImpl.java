package core.basesyntax.store.service.impl;

import core.basesyntax.store.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterImpl implements FileWriter {

    @Override
    public void write(String report, String fileName) {
        Path path = Path.of(fileName);
        try {
            Files.writeString(path, report);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file: " + path, e);
        }
    }
}
