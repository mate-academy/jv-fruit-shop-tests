package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_REPORT = "fruit,quantity\n"
            + "banana,20";
    public static final String VALID_DATA_PATH = "src/test/resources/data.csv";
    public static final String VALID_TEST_REPORT_PATH = "src/test/resources/testReport.csv";
    private static FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    public void writeReport_ok() {
        fileWriterService.writeReport(VALID_TEST_REPORT_PATH, VALID_REPORT);
    }
}
