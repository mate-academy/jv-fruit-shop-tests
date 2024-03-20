package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String CORRECT_HEADER = "fruit,quantity";
    private static final int HEADER_LINE_INDEX = 0;
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
                                                    + "banana,152" + System.lineSeparator()
                                                        + "apple,90";
    private static ReportServiceImpl reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("banana",152);
        Storage.fruits.put("apple",90);
    }

    @Test
    public void generateReport_ok() {
        String actualReport = reportService.generateReport();
        assertEquals(EXPECTED_REPORT,actualReport);
    }

    @Test
    public void createHeaderInReport_ok() {
        String[] lines = reportService.generateReport().split(System.lineSeparator());
        String expected = CORRECT_HEADER;
        String actual = lines[HEADER_LINE_INDEX];
        assertEquals("Expected header line: " + expected + ", but was: " + actual,
                expected, actual);
    }
}
