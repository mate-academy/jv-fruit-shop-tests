package core.basesyntax.service;

import core.basesyntax.service.impl.FileCsvWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileCsvWriterImplTest {
    private static final String TEST_REPORT_FILE = "src/test/resources/test_report.csv";
    private static final String WRONG_PATH = "src/tast/resources/report.csv";
    private static final Path PATH_REPORT_FILE = Path.of("src/test/resources/report.csv");
    private static final Path PATH_TEST_REPORT_FILE = Path.of(TEST_REPORT_FILE);
    private static FileCsvWriter fileCsvWriter;
    private static String testReport;

    @BeforeClass
    public static void setUp() {
        fileCsvWriter = new FileCsvWriterImpl();
        testReport = "fruit,quantity"
                + System.lineSeparator()
                + "banana,107"
                + System.lineSeparator()
                + "apple,110";
    }

    @Test
    public void writeToFile_ok() throws IOException {
        fileCsvWriter.writeToFile(testReport, TEST_REPORT_FILE);
        Assert.assertEquals(Files.readAllLines(PATH_TEST_REPORT_FILE),
                Files.readAllLines(PATH_REPORT_FILE));
        Files.deleteIfExists(PATH_TEST_REPORT_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void writeToWrongFile_notOk() {
        fileCsvWriter.writeToFile(testReport, WRONG_PATH);
    }
}
