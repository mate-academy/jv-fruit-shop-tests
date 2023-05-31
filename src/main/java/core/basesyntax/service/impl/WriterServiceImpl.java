package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriterServiceImpl implements WriterService {
    @Override
    public boolean writeToFile(String filePath, String report) {
        try {
            Files.write(Path.of(filePath), report.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + filePath, e);
        }
        return true;
    }
}
