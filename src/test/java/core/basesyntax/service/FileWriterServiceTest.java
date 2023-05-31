package core.basesyntax.service;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String TO_FILE_PATH = "src/test/resources/report.csv";
    private static FileWriterService fileWriterService;
    private static final String REPORT = "fruit,quantity";

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeDataToFile_NullPath_NotOk() {
        fileWriterService.writeDataToFile(REPORT, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeDataToFile_NullReport_NotOk() {
        fileWriterService.writeDataToFile(null, REPORT);
    }

    @Test
    public void writeDataToFile_ValidInput_Ok() {
        fileWriterService.writeDataToFile(REPORT, TO_FILE_PATH);
    }
}
