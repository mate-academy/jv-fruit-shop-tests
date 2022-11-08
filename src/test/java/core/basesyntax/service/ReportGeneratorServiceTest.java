package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorServiceImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorServiceTest {
    private static final String VALID_REPORT = "apple,30";
    private static final String APPLE_KEY = "apple";
    private static final Integer APPLE_VALUE = 30;
    private static final String EXPECTED_LINE_WITH_SEPARATOR = "fruit,quantity"
            + System.lineSeparator();
    private static final String EXPECTED_LINE = "fruit,quantity";

    private static ReportGeneratorService report;

    @BeforeClass
    public static void beforeClass() {
        report = new ReportGeneratorServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void generateReportFromEmptyDataBase_ok() {
        String actual = report.reportGenerate();
        assertEquals(EXPECTED_LINE, actual);
    }

    @Test
    public void generateReportFromValidBase_ok() {
        Storage.fruitStorage.put(APPLE_KEY, APPLE_VALUE);
        String actual = report.reportGenerate();
        String expected = EXPECTED_LINE_WITH_SEPARATOR + VALID_REPORT;
        assertEquals(expected, actual);
    }
}
