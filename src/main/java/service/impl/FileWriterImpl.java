package service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterImpl {
    public void write(String filePath, String content) {
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(filePath))) {
            bufferWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + filePath, e);
        }
    }
}
