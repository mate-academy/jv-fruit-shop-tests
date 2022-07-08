package core.basesyntax.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeFile(String data, String fileName) {
        try {
            if (Files.exists(Path.of(fileName))) {
                throw new RuntimeException("File " + fileName + " already exists");
            }
            Files.write(Path.of(fileName), data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Can't write report to file: " + fileName, e);
        }
    }
}
