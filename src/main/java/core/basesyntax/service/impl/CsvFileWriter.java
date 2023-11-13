package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriter implements FileWriterService {
    private static final String RUNTIME_EXCEPTION_MESSAGE = "Can't write to file with path: ";
    private static final String NULL_CONTENT_MESSAGE = "Content cannot be null";
    private static final String EMPTY_CONTENT_MESSAGE = "Content cannot be empty";

    @Override
    public boolean write(String content, String filePath) {
        if (content == null) {
            throw new IllegalArgumentException(NULL_CONTENT_MESSAGE);
        }
        if (content.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_CONTENT_MESSAGE);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(RUNTIME_EXCEPTION_MESSAGE + filePath, e);
        }
    }
}
