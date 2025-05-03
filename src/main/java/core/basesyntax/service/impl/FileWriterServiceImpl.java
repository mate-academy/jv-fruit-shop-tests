package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.exception.NoSuchFileException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String report, String path) {
        if (report == null) {
            throw new InvalidDataException("Can't write null data to file");
        }
        if (path == null) {
            throw new NoSuchFileException("Can't find file with null path");
        }
        try {
            Files.writeString(Path.of(path), report);
        } catch (IOException e) {
            throw new NoSuchFileException("Can't write data to file: " + path, e);
        }
    }
}
