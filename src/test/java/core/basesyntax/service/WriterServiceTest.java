package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String OUTPUT_FILE_PATH = "src/test/resources/outputFile.csv";
    private static WriterService writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_validPath_ok() {
        List<String> expectedReport = List.of("fruit,quantity", "banana,152", "apple,90");
        writer.writeToFile(OUTPUT_FILE_PATH, expectedReport);
        List<String> actualReport = readFromTestFile(OUTPUT_FILE_PATH);
        Assert.assertEquals(expectedReport, actualReport);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullPath_notOk() {
        List<String> expectedReport = List.of("fruit,quantity", "banana,152", "apple,90");
        writer.writeToFile(null, expectedReport);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullReport_notOk() {
        List<String> expectedReport = List.of("fruit,quantity", "banana,152", "apple,90");
        writer.writeToFile(OUTPUT_FILE_PATH, null);
    }

    @Test
    public void writeToFile_emptyReport_ok() {
        List<String> expectedReport = List.of();
        writer.writeToFile(OUTPUT_FILE_PATH, expectedReport);
        List<String> actualReport = readFromTestFile(OUTPUT_FILE_PATH);
        Assert.assertEquals(expectedReport, actualReport);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_emptyPath_NotOk() {
        List<String> expectedReport = List.of("fruit,quantity", "banana,152", "apple,90");
        writer.writeToFile("", expectedReport);
    }

    private static List<String> readFromTestFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + fileName, e);
        }
    }
}
