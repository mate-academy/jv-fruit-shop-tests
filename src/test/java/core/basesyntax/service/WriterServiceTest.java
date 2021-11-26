package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static WriterService writerService;
    private static final String EXPECTED_OUTPUT_FILE_PATH
            = "src/test/resources/outputExpectedFile.csv";
    private static final String ACTUAL_OUTPUT_FILE_PATH
            = "src/test/resources/outputActualFile.csv";
    private static final String EXPECTED_EMPTY_OUTPUT_FILE_PATH
            = "src/test/resources/emptyFile.csv";
    private static final String ACTUAL_EMPTY_OUTPUT_FILE_PATH
            = "src/test/resources/emptySecondFile.csv";
    private static String expected;
    private static String actual;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        expected = "";
        actual = "";
    }

    @Test
    public void writeValidReportToValidPath_Ok() {
        try {
            actual = Files.readString(Path.of(EXPECTED_OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter(ACTUAL_OUTPUT_FILE_PATH))) {
            bufferedWriter.write(actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file!", e);
        }
        try {
            expected = Files.readString(Path.of(ACTUAL_OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void writeEmptyReportToValidPath_Ok() {
        try {
            actual = Files.readString(Path.of(EXPECTED_EMPTY_OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter(ACTUAL_EMPTY_OUTPUT_FILE_PATH))) {
            bufferedWriter.write(actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file!", e);
        }
        try {
            expected = Files.readString(Path.of(ACTUAL_EMPTY_OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        Assert.assertEquals(expected, actual);
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
        writerService.writeToFile(EXPECTED_OUTPUT_FILE_PATH, null);
    }
}
