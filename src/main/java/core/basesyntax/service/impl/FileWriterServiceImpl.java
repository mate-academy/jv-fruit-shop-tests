package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String data, String path) {
        checkData(data);
        try {
            Files.writeString(Path.of(path), data);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data to file" + path, e);
        }
    }

    private void checkData(String data) {
        if (data.equals("")) {
            throw new RuntimeException("Empty data");
        }
    }
}
