package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileWriterService;
import java.io.File;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static CsvFileWriterService writerService;
    private static final String REPORT = "Hello, world!";
    private static final String NOT_VALID_PATH = "src/main/java/reports/report.csv";
    private static final String VALID_PATH = "src/test/resources/testReport.csv";

    @BeforeClass
    public static void init() {
        writerService = new CsvFileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_NotValidPath_NotOk() {
        writerService.writeToFile(REPORT, NOT_VALID_PATH);
    }

    @Test
    public void writeToFile_ReportFileExists_Ok() {
        writerService.writeToFile(REPORT, VALID_PATH);
        File file = new File(VALID_PATH);
        Assert.assertTrue(file.exists());
    }
}
