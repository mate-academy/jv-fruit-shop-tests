package core.basesyntax.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileWriter implements WriterToFile {
    private static final String EXCEPTION_WRITE_DATA_MESSAGE = "Can't write data to file";

    @Override
    public void writeFile(List<String> report, String filepath) {
        File file = new File(filepath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : report) {
                writer.write(line);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_WRITE_DATA_MESSAGE, e);
        }
    }
}
