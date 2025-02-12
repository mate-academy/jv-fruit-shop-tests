package service;

import java.io.FileWriter;
import java.io.IOException;

public class CsvWriteService implements Writer {

    @Override
    public void writeReport(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException ex) {
            throw new RuntimeException("Error writing to CSV file: " + fileName);
        }
    }
}
