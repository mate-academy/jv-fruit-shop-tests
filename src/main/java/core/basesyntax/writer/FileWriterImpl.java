package core.basesyntax.writer;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriterImpl implements FileWriter {
    @Override
    public void write(String report, String fileName) {
        if (report == null || report.isBlank()) {
            throw new IllegalArgumentException("Report can't be null or blank");
        }

        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name can't be null or blank");

        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(fileName))) {
            bufferedWriter.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file: " + fileName, e);
        }
    }
}
