package core.basesyntax.store.io.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterImpl implements FileWriter {
    private static final String RESOURCE_FOLDER = "src/test/resources/";

    @Override
    public void write(String report, String fileName) {
        Path path = Path.of(RESOURCE_FOLDER, fileName);
        try {
            Files.writeString(path, report);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file: " + path, e);
        }
    }
}
