package core.basesyntax;

import core.basesyntax.service.impl.CsvFileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FileWriterTest {
    private CsvFileWriter csvFileWriter;

    @Before
    public void setUp() {
        csvFileWriter = new CsvFileWriter();
    }

    @Test
    public void write_ValidData_Ok() {
        String report = "Test report data";
        String fileName = "src/test/resources/test_output.csv";
        String initialData = "Initial data in the file";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(initialData);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + fileName, e);
        }
        csvFileWriter.write(report, fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            Assertions.assertEquals(report, line, "Content should match what was written!");
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + fileName, e);
        }
    }
}
