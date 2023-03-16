package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String CORRECT_LINE = "apple,5";
    private static final String CORRECT_FRUIT = "apple";
    private static final String CORRECT_HEADER = "fruit,quantity";
    private static final int CORRECT_QUANTITY = 5;
    private static final int DATA_LINE_INDEX = 1;
    private static final int HEADER_LINE_INDEX = 0;
    private ReportServiceImpl reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        Storage.fruits.clear();
    }

    @Test
    public void generateReport_ok() {
        Storage.put(CORRECT_FRUIT, CORRECT_QUANTITY);
        String[] lines = reportService.generateReport().split(System.lineSeparator());
        String expected = CORRECT_LINE;
        String actual = lines[DATA_LINE_INDEX];
        assertEquals("Expected line: " + expected + ", but was: " + actual,
                expected, actual);
    }

    @Test
    public void createHeaderInReport_ok() {
        Storage.put(CORRECT_FRUIT, CORRECT_QUANTITY);
        String[] lines = reportService.generateReport().split(System.lineSeparator());
        String expected = CORRECT_HEADER;
        String actual = lines[HEADER_LINE_INDEX];
        assertEquals("Expected header line: " + expected + ", but was: " + actual,
                expected, actual);
    }
}
