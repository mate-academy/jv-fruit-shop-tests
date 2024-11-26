package core.basesyntax.fileprocessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class CsvFileReader extends FileReader {
    @Override
    public List<String> read(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("File path must not be null or empty");
        }
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (NoSuchFileException e) {
            throw new RuntimeException("Failed to read the file at path: " + filePath, e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file at path: " + filePath, e);
        }
    }
}
