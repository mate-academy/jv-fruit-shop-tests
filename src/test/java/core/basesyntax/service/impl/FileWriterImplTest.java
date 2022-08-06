package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

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
            throw new RuntimeException("Can't read data from report file");
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_emptyPath_notOk() {
        fileWriter.writeReport("", "banana,99", HEADER);
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_nullPath_notOk() {
        fileWriter.writeReport(null, "apple,90", HEADER);
    }
}
