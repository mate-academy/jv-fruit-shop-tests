package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static WriterService writerService;
    private static final String OUTPUT_FILE_PATH = "src/test/resources/outputFile.csv";
    private static String expected;
    private static String actual;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        expected = "fruit,quantity" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "apple,80";
        actual = "";
    }

    @Test
    public void writeValidReportToValidPath_Ok() {
        writerService.writeToFile(OUTPUT_FILE_PATH, expected);
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeEmptyReportToValidPath_Ok() {
        String emptyReport = "";
        writerService.writeToFile(OUTPUT_FILE_PATH, emptyReport);
        try {
            actual = Files.readString(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        Assert.assertEquals(emptyReport, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToNullPath_notOk() {
        writerService.writeToFile(null, expected);
    }

    @Test (expected = RuntimeException.class)
    public void writeToEmptyPath_notOk() {
        String empryFilePath = "";
        writerService.writeToFile(empryFilePath, expected);
    }

    @Test(expected = RuntimeException.class)
    public void writeToWrongPath_NotOk() {
        String wrongFilePath = "src/test/resources/inn/111/!!!/not_a_path";
        writerService.writeToFile(wrongFilePath, expected);
    }

    @Test(expected = RuntimeException.class)
    public void writeNullReport_NotOk() {
        writerService.writeToFile(OUTPUT_FILE_PATH, null);
    }
}
