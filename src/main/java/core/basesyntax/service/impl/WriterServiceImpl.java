package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WriterServiceImpl implements WriterService {
    @Override
    public boolean write(String dataToFile, String path) {
        if (dataToFile == null || path == null) {
            throw new RuntimeException("Data or path can't be null. Data: " + dataToFile
                    + ", path: " + path);
        }
        try {
            Files.writeString(Paths.get(path), dataToFile);
        } catch (IOException e) {
            throw new RuntimeException("Can't write transactions to file", e);
        }
        return true;
    }
}
