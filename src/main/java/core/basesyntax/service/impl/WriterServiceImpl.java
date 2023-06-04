package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
//
public class WriterServiceImpl implements WriterService {
    @Override
    public void writeToCsvFile(String filePath, String reportData) {
        try {
            Files.writeString(Path.of(filePath), reportData);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + filePath, e);
        }
    }
}
