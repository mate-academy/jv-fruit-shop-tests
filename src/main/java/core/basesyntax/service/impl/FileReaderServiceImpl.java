package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidPathException;
import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public String readFromFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new InvalidPathException("Can't find file by path: " + path);
        }
    }
}
