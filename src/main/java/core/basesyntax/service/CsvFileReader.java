package core.basesyntax.service;

import static java.nio.file.Files.readAllLines;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class CsvFileReader implements FileReader {
    @Override
    public List<String> read(String fileName) {
        try {
            return readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + fileName, e);
        }
    }
}
