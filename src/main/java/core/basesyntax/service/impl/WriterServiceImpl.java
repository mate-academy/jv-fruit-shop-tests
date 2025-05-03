package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriterServiceImpl implements WriterService {
    private static final String FILE_WRITE_EXCEPTION_MESSAGE = "Can't write to file: ";
    private static final String WRITE_NULL_EXCEPTION_MESSAGE = "file Path can't be null";

    @Override
    public void writeToFile(String fileName, String content) {
        if (fileName == null) {
            throw new RuntimeException(WRITE_NULL_EXCEPTION_MESSAGE);
        }
        Path path = Paths.get(fileName);
        try {
            Files.writeString(path, content);
        } catch (IOException e) {
            throw new RuntimeException(FILE_WRITE_EXCEPTION_MESSAGE + fileName, e);
        }
    }
}
