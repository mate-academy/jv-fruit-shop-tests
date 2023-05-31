package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NullFileException;
import core.basesyntax.exceptions.ReadDataException;
import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CsvFileReader implements FileReaderService {
    @Override
    public List<String> readFromFile(File file) {
        if (file == null) {
            throw new NullFileException("Incoming file is null!");
        }

        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new ReadDataException("Can't read data from file " + file);
        }
    }
}
