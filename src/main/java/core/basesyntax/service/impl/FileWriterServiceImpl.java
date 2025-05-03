package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String path, String report) {
        if (Files.exists(Path.of(path))) {
            try {
                Files.writeString(Path.of(path), report);
            } catch (IOException e) {
                throw new RuntimeException("Report can't be written to " + path, e);
            }
        } else {
            throw new RuntimeException("Wrong path: " + path);
        }
    }
}
