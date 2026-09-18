package core.basesyntax.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String filePath, String report) {
        try {
            Files.writeString(Path.of(filePath), report);
        } catch (IOException exception) {
            throw new RuntimeException("Can not write to file: " + filePath, exception);
        }
    }
}
