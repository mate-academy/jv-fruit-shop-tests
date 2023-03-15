package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceTest {
    private static final String HEADER = "fruit,quantity";
    private static final String LINE_OK = "apple,5";
    private static final String APPLE = "apple";
    private static final Integer FIVE = 5;
    private static final int HEADER_LINE_INDEX = 0;
    private static final int FIRST_DATA_LINE_INDEX = 1;
    private static ReportGeneratorServiceImpl reportGeneratorService;

    @BeforeClass
    public static void beforeClass() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
        Storage.storage.clear();
    }

    @Test
    public void createReport_Header_ok() {
        Storage.storage.put(APPLE, FIVE);
        String[] lines = reportGeneratorService.generateReportText(Storage.storage)
                .split(System.lineSeparator());
        String expected = HEADER;
        String actual = lines[HEADER_LINE_INDEX];
        assertEquals("Expected header line: " + expected + ", but was: " + actual,
                expected, actual);
    }

    @Test
    public void createReport_ok() {
        Storage.storage.put(APPLE, FIVE);
        String[] lines = reportGeneratorService.generateReportText(Storage.storage)
                .split(System.lineSeparator());
        String expected = LINE_OK;
        String actual = lines[FIRST_DATA_LINE_INDEX];
        assertEquals("Expected line: " + expected + ", but was: " + actual, expected, actual);
    }
}
