package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;

public class WriterServiceImpl implements WriterService {
    @Override
    public void writeToFile(String fileName, String report) {
        try {
            Files.writeString(new File(fileName).toPath(), report);
        } catch (InvalidPathException | IOException e) {
            throw new RuntimeException("Can't write to file " + fileName, e);
        }
    }
}
