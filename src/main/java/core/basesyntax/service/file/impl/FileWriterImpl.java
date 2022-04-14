package core.basesyntax.service.file.impl;

import core.basesyntax.service.file.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterImpl implements FileWriter {
    private static final String ERROR_MESSAGE = "Can't write to file in this path";

    @Override
    public void write(String path, String report) {
        try {
            Files.writeString(Path.of(path), report);
        } catch (IOException e) {
            throw new RuntimeException(ERROR_MESSAGE + " " + path, e);
        }
    }
}
