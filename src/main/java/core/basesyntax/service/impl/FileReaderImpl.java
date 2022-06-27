package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderImpl implements FileReaderService {
    @Override
    public List<String> readDataFromFile(String pathName) {
        try {
            return Files.readAllLines(Path.of(pathName));
        } catch (IOException e) {
            throw new RuntimeException("Can't extract data from file correctly " + pathName, e);
        }
    }
}
