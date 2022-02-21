package core.basesyntax.dao.impl;

import core.basesyntax.dao.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvReaderImpl implements FileReader {

    @Override
    public List<String> parse(String filePath) {
        try {
            if (!Files.readAllLines(Path.of(filePath)).isEmpty()) {
                return Files.readAllLines(Path.of(filePath));
            }
            throw new RuntimeException("file: " + filePath + " is empty");
        } catch (IOException e) {
            throw new RuntimeException("cant read from file: " + filePath);
        }
    }
}
