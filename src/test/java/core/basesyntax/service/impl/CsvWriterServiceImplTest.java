package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvWriterServiceImplTest {
    private static CsvWriterServiceImpl csvWriterService;

    @BeforeClass
    public static void beforeClass() {
        csvWriterService = new CsvWriterServiceImpl();
    }

    @Test
    public void writeToFile_Ok_notOk() {
        String reportFilePath = "";
        String data = "apple,100";
        try {
            csvWriterService.writeToFile(reportFilePath, data);
        } catch (RuntimeException e) {
            return;
        }
        fail("Need to throw RuntimeException with empty path!");
    }

    @Test
    public void writeToFile_Ok() {
        String actual;
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,50";
        String reportFilePath = "src/main/resources/report.csv";
        String data = "apple,100" + System.lineSeparator() + "banana,50";
        csvWriterService.writeToFile(reportFilePath, data);
        try {
            actual = Files.readString(Path.of(reportFilePath));
            Files.delete(Path.of(reportFilePath));
        } catch (IOException e) {
            throw new RuntimeException("Could not read data from file " + reportFilePath, e);
        }
        assertEquals(expected, actual);
    }
}
