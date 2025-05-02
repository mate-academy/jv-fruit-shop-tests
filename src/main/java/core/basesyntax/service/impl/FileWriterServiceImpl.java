package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String content, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filePath))) {
                writer.write(content);
            } catch (IOException e) {
                throw new RuntimeException("Error writing to file: " + filePath, e);
            }
        } else {
            throw new RuntimeException("File not found: " + filePath);
        }
    }
}
