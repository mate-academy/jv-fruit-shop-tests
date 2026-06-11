package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReaderImpl implements FileReader {
    @Override
    public List<String> readAllLines(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the file " + filePath, e);
        }
    }
}
