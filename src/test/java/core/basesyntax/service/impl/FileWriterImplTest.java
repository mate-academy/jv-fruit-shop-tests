package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static String report;

    @BeforeClass
    public static void setUp() {
        report = "banana,152" + System.lineSeparator() + "apple,90";
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeReport_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("banana,152");
        expected.add("apple,90");
        String pathToFile = "src/test/resources/report.csv";
        fileWriter.writeReport(pathToFile, report);
        try {
            Path filePath = Path.of(pathToFile);
            List<String> actual = Files.readAllLines(filePath);
            assertEquals(actual, expected);
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from report file by path: " + pathToFile);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_emptyPath_notOk() {
        fileWriter.writeReport("", "banana,99");
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_nullPath_notOk() {
        fileWriter.writeReport(null, "apple,90");
    }
}
