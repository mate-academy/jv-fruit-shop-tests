package core.basesyntax.service.impl;

import core.basesyntax.exception.IllegalInputDataException;
import core.basesyntax.exception.ReadFromFileException;
import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvFileReader implements FileReader {
    @Override
    public List<String> readData(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalInputDataException("Input file path is null or empty");
        }

        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException ex) {
            throw new ReadFromFileException(String.format("Can`t read data from the file %s",
                    filePath), ex);
        }
    }
}
