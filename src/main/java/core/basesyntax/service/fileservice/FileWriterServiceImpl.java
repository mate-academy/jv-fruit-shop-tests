package core.basesyntax.service.fileservice;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void write(String data, String filePath) {
        if (data == null || filePath == null) {
            throw new RuntimeException("Data or filePath is null");
        }
        try (BufferedWriter fileWriter = new BufferedWriter(
                new FileWriter(filePath))) {
            fileWriter.append(data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file!", e);
        }
    }
}
