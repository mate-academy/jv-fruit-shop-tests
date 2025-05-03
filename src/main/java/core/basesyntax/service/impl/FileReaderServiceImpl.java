package core.basesyntax.service.impl;

import core.basesyntax.exception.FileReaderException;
import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public List<String> readFromFile(String filePath) {
        if (filePath == null) {
            throw new FileReaderException("Null file path");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileReaderException("No such file exists");
        }
        Path path = Paths.get(filePath);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + filePath, e);
        }
    }
}
