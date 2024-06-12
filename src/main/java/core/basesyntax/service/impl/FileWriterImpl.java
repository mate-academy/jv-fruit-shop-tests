package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String content, String filePath) {
        Path path = Paths.get(filePath);

        if (content.isEmpty() || !Files.exists(path)) {
            throw new RuntimeException("Can't write to file by path - " + filePath);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file by path - " + filePath, e);
        }
    }
}
