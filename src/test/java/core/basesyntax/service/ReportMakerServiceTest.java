package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerServiceTest {
    private static final String HEADER = "fruit,quantity";
    private static final String LINE_OK = "apple,5";
    private static final String APPLE = "apple";
    private static final Integer FIVE = 5;

    private static ReportMakerService reportMakerService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportMakerService = new ReportMakerService();
        Storage.fruits.clear();
    }

    @Test
    public void createReport_Header_ok() {
        String actual = reportMakerService.createReport();
        assertTrue("String must contain header line, but wasn't", actual.contains(HEADER));
    }

    @Test
    public void createReport_ok() {
        Storage.put(APPLE, FIVE);
        String actual = reportMakerService.createReport();
        String expected = LINE_OK;
        assertTrue("Expected found line" + expected + ", but wasn't", actual.contains(expected));
    }
}
