package core.basesyntax.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportWriterImpl implements ReportWriter {

    public void writeToFile(List<String> lines, String fileName) {
        if (lines == null) {
            throw new RuntimeException("Can't write NULL to file by path: " + fileName);
        }
        if (fileName == null) {
            throw new RuntimeException("Can't write data to file by path: " + fileName);
        }
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String l : lines) {
                writer.write(l + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + fileName, e);
        }
    }
}
