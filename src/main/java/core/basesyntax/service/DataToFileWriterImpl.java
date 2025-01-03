package core.basesyntax.service;

import java.io.FileWriter;
import java.io.IOException;

public class DataToFileWriterImpl implements DataToFileWriter {
    @Override
    public void writeData(String report, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + fileName, e);
        }
    }
}
