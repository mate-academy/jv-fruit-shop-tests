package core.basesyntax.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterImpl implements FileWriter {

    @Override
    public void write(String resultingReport, String fileName) {
        if (resultingReport == null || resultingReport.isEmpty()) {
            throw new RuntimeException("Resulting report is empty" + resultingReport);
        }
        if (fileName == null || fileName.isEmpty()) {
            throw new RuntimeException("File name is empty" + fileName);
        }

        try {
            Files.writeString(Path.of(fileName), resultingReport);
        } catch (IOException e) {
            throw new RuntimeException("Error while writing resulting report to " + fileName, e);
        }
    }
}
