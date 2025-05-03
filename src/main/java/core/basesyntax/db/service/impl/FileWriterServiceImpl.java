package core.basesyntax.db.service.impl;

import core.basesyntax.db.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String report, String path) {
        Path pathToFile = Path.of(path);
        if (report == null) {
            throw new NullPointerException("Report can't be null");
        }
        if (Files.exists(pathToFile)) {
            try {
                Files.writeString(pathToFile, report);
            } catch (IOException e) {
                throw new RuntimeException("Can't write data to file", e);
            }
        } else {
            throw new RuntimeException("Can't find path " + path);
        }
    }
}
