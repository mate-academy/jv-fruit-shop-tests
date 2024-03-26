package core.basesyntax.service.impl;

import core.basesyntax.exception.CsvFileException;
import core.basesyntax.exception.CsvIllegalArgumentException;
import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvWriter implements FileWriter {
    @Override
    public void writeToFile(Path path, List<String> data) {
        if (path == null || data == null) {
            throw new CsvIllegalArgumentException("Path and data must not be null");
        }
        try {
            Files.write(path, data);
        } catch (IOException e) {
            throw new CsvFileException("Cannot write to CSV file in directory path" + path, e);
        }
    }
}
