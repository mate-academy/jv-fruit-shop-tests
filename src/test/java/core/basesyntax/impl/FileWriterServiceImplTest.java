package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.servise.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;
    private static String report;
    private static List<String> expected;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
        report = "fruit, quantity" + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
    }

    @Test
    public void writeToFileWithValidPath_ok() {
        fileWriterService.writeFile("src/test/resources/report.csv", report);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of("src/test/resources/report.csv"));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file "
                    + "src/test/resources/report.csv", e);
        }
        expected = new ArrayList<>();
        expected.add("fruit, quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFileWithInvalidPath_notOk() {
        fileWriterService.writeFile(null, report);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFileNullReport_notOk() {
        fileWriterService.writeFile("src/test/resources/report.csv", null);
    }
}
