package core.basesyntax.fileservice;

import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter implements Writer {
    @Override
    public void writeToFile(String fileName, String data) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(data);
            writer.flush();
        } catch (IOException ex) {
            throw new RuntimeException("Can`t write to file! " + fileName);
        }
    }
}
