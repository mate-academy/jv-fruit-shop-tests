package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorServiceImpl;
import org.junit.After;
import org.junit.Before;
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
    private String[] lines;

    @BeforeClass
    public static void beforeClass() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
    }

    @Before
    public void beforeEach() {
        Storage.storage.put(APPLE, FIVE);
        lines = reportGeneratorService.generateReportText(Storage.storage)
                .split(System.lineSeparator());
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }

    @Test
    public void createReport_Header_ok() {
        String expected = HEADER;
        String actual = lines[HEADER_LINE_INDEX];
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_ok() {
        String expected = LINE_OK;
        String actual = lines[FIRST_DATA_LINE_INDEX];
        assertEquals(expected, actual);
    }
}
