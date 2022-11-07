package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriterServiceImpl implements WriterService {
    @Override
    public boolean write(String fileName, String record) {
        try {
            Files.write(Path.of(fileName), record.getBytes());
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Can't write data in file " + fileName, e);
        }
        return true;
    }
}
