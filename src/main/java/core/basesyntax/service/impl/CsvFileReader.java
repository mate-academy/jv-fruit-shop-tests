package core.basesyntax.service.impl;

import core.basesyntax.exception.ReadFromFileException;
import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class CsvFileReader implements FileReader {
    @Override
    public List<String> readData(String filePath) {
        Objects.requireNonNull(filePath);
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException ex) {
            throw new ReadFromFileException(String.format("Can`t read data from the file %s",
                    filePath), ex);
        }
    }
}
