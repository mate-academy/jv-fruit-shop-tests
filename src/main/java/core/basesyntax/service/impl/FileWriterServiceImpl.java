package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidPathException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String path, String report) {
        if (path == null) {
            throw new InvalidPathException("Path can't be null");
        }
        try {
            Files.writeString(Path.of(path), report);
        } catch (IOException e) {
            throw new InvalidPathException("Can't write to file " + path, e);
        }
    }
}
