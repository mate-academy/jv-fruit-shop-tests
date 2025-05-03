package core.basesyntax.file.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterImpl implements FileWriterInterface {
    @Override
    public void write(String report, String filePath) {
        if (report == null) {
            throw new IllegalArgumentException("Report should not be null");
        }
        if (filePath == null) {
            throw new IllegalArgumentException("File path should not be null");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(report);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error generating file " + filePath, e);
        }
    }
}
