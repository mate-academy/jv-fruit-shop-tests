package core.basesyntax.service.impl;

import core.basesyntax.exception.FileWriterException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String text, String createdFilePath) {
        if (text == null || createdFilePath == null) {
            throw new FileWriterException("Null input");
        }
        try {
            Files.write(Paths.get(createdFilePath), text.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + createdFilePath, e);
        }
    }
}
