package core.basesyntax.service.impl;

import core.basesyntax.exception.FileWritingException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_REPORT = "fruit,quantity\n"
            + "banana,20";
    private static final String VALID_TEST_REPORT_PATH = "src/test/resources/testReport.csv";
    private static final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    public void writeReport_validPathAndValidReport_ok() {
        fileWriterService.writeReport(VALID_TEST_REPORT_PATH, VALID_REPORT);
        String actual;
        try {
            actual = Files.readString(Path.of(VALID_TEST_REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read report from path: ", e);
        }
        Assert.assertEquals("Report data have to be "
                + "similar to validReport.csv", VALID_REPORT, actual);
    }

    @Test
    public void writeReport_validPathAndEmptyReport_ok() {
        String emptyDataReport = "";
        fileWriterService.writeReport(VALID_TEST_REPORT_PATH, emptyDataReport);
        String actual;
        try {
            actual = Files.readString(Path.of(VALID_TEST_REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read report from path: ", e);
        }
        Assert.assertEquals("Report data have to be "
                + "similar to validReport.csv", emptyDataReport, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullPath_nullReport_notOk() {
        fileWriterService.writeReport(null, null);
    }

    @Test(expected = FileWritingException.class)
    public void writeToFile_emptyPath_notOk() {
        fileWriterService.writeReport("", VALID_REPORT);
    }

    @AfterClass
    public static void clearFile() {
        try {
            Files.writeString(Path.of(VALID_TEST_REPORT_PATH), "");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't delete the report data: "
                    + VALID_TEST_REPORT_PATH, e);
        }
    }
}
