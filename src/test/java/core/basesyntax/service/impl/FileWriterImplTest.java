package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String HEADER = "fruit,quantity";
    private FileWriter fileWriter;
    private String report;

    @Before
    public void setUp() {
        report = System.lineSeparator() + "banana,152" + System.lineSeparator() + "apple,90";
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeReport_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        fileWriter.writeReport("src/main/resources/report.csv", report, HEADER);
        try {
            Path filePath = Path.of("src/main/resources/report.csv");
            List<String> actual = Files.readAllLines(filePath);
            assertEquals(actual, expected);
            Files.delete(filePath);
        } catch (IOException e) {
            fail("Can't read data from report file");
        }
    }

    @Test
    public void writeReport_emptyPath_notOk() {
        try {
            fileWriter.writeReport("", "banana,99", HEADER);
        } catch (RuntimeException e) {
            return;
        }
        fail("Cannot write data to file by empty path");
    }

    @Test
    public void writeReport_nullPath_notOk() {
        try {
            fileWriter.writeReport(null, "apple,90", HEADER);
        } catch (RuntimeException e) {
            return;
        }
        fail("Cannot write data to file by null path");
    }
}
