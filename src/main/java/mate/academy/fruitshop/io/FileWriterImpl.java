package mate.academy.fruitshop.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterImpl implements FileWriter {

    @Override
    public void write(String data, String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new RuntimeException("File path cannot be null or empty");
        }

        if (data == null) {
            throw new RuntimeException("Data cannot be null");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file: " + filePath, e);
        }
    }
}
