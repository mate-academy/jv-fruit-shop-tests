package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class CsvFileReader implements FileReader {
    @Override
    public List<String> read(String filePath) throws NoSuchFileException {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (NoSuchFileException e) {
            throw e;
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
}
