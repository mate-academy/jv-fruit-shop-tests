package core.basesyntax.service;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriterImpl implements FileWriter {
    @Override
    public boolean write(String data, String path) {
        if (path == null || path.isEmpty()) {
            throw new RuntimeException("The writing path is not correct" + path);
        }
        if (data == null || data.isEmpty()) {
            throw new RuntimeException("Incorrect data in the string to write" + data);
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(path))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            throw new RuntimeException("The writing path is not correct.");
        }
        return true;
    }
}
