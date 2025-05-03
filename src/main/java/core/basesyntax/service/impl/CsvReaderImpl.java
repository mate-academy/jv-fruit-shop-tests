package core.basesyntax.service.impl;

import core.basesyntax.service.CsvReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvReaderImpl implements CsvReader {
    @Override
    public List<String> read(String filePath) {
        if (filePath == null) {
            throw new NullPointerException("File name cannot be null");
        }
        if (!filePath.endsWith(".csv")) {
            throw new IllegalArgumentException("File must have the .csv format");
        }
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + filePath, e);
        }
    }
}
