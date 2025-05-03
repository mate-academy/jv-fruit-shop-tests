package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeFile(String data, String path) {
        if (data.equals("")) {
            throw new RuntimeException("Can't write EMPTY data " + data + " to file: " + path);
        }
        try {
            Files.writeString(Path.of(path), data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data " + data + " to file: " + path, e);
        }
    }
}
