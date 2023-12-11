package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    private static final String RUNTIME_EXCEPTION_MESSAGE = "Can't read from file"
            + "Something went wrong";

    @Override
    public List<String> readFromFile(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(RUNTIME_EXCEPTION_MESSAGE + filePath, e);
        }
    }
}

