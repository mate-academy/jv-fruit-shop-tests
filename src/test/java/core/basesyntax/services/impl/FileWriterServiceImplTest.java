package core.basesyntax.services.impl;

import core.basesyntax.services.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/report.csv";
    private static final String CORRECT_TEST_REPORT = "src/test/resources/correct_report.csv";
    private static final String INCORRECT_FILE_PATH = "////";
    private static final String INCORRECT_REPORT = null;
    private static final String CORRECT_REPORT = "fruit,quantity\n"
            + "banana, 152\n"
            + "apple, 90";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void write_CorrectReportAndFilePath() {
        fileWriterService.write(CORRECT_REPORT, CORRECT_FILE_PATH);
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(CORRECT_TEST_REPORT));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read from file " + CORRECT_TEST_REPORT, e);
        }
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(CORRECT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read from file " + CORRECT_FILE_PATH, e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_CorrectReportIncorrectFilePath() {
        fileWriterService.write(CORRECT_REPORT, INCORRECT_FILE_PATH);
    }

    @Test(expected = NullPointerException.class)
    public void write_IncorrectReportCorrectFilePath() {
        fileWriterService.write(INCORRECT_REPORT, CORRECT_FILE_PATH);
    }
}
