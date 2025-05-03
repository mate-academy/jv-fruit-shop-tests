package core.basesyntax.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriterImpl implements core.basesyntax.service.FileWriter {
    @Override
    public void write(String report, String filePath) {
        if (report == null) {
            throw new IllegalArgumentException("Cannot write a null report!");
        }
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(report);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while writing data to the file: "
                    + filePath, e);
        }
    }
}
