package core.basesyntax.impl;

import core.basesyntax.service.WriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriterServiceImpl implements WriterService {
    private static final String ALLOWED_DIRECTORY = "src/test/resources";

    @Override
    public void writeValueToFile(String filePath, String value) {
        Path path = Paths.get(filePath);
        if (!path.startsWith(ALLOWED_DIRECTORY)) {
            throw new IllegalArgumentException("File path is not allowed: " + filePath);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(value);
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + filePath, e);
        }
    }
}
