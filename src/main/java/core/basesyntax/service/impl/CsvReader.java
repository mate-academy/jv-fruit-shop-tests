package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvReader implements Reader {
    private static final String FORMAT_FILE = "csv";

    @Override
    public List<String> readFile(String pathToFile) {
        validate(pathToFile);
        try {
            File file = new File(pathToFile);
            String name = file.getName();
            String format = name.substring(name.indexOf('.') + 1);
            if (!format.equals(FORMAT_FILE)) {
                throw new RuntimeException("Invalid format file: " + format);
            }
            return Files.readAllLines(Path.of(file.toURI()));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file " + pathToFile);
        }
    }

    private void validate(String pathToFile) {
        if (pathToFile == null) {
            throw new RuntimeException("Path to file must not be null.");
        }
    }
}
