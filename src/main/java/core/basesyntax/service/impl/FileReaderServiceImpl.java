package core.basesyntax.service.impl;

import core.basesyntax.exception.NoSuchFileException;
import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReaderServiceImpl implements FileReaderService {
    public String readFromFile(String fileName) {
        if (fileName == null) {
            throw new NoSuchFileException("Can't find file with null path");
        }
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new NoSuchFileException("Can't find file: " + fileName, e);
        }
    }
}
