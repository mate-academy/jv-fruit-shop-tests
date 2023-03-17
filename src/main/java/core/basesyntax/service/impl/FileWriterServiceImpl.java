package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String fileName, String data) {
        if (fileName == null) {
            throw new RuntimeException("Can't write data to null file");
        }
        if (data == null || data.isEmpty()) {
            throw new RuntimeException("Can't write null or empty data to file");
        }
        File file = new File(fileName);
        try {
            Files.write(file.toPath(), data.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file " + file, e);
        }
    }
}
