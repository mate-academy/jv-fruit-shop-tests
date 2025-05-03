package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String COLUMNS_TITLE = "fruit,quantity";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private ReportService reportService = new ReportServiceImpl();

    @Test
    void getReportFromSet1_Ok() {
        Storage.FRUITS.put("apple", 50);
        Storage.FRUITS.put("banana", 60);
        Storage.FRUITS.put("orange", 70);
        String expected = COLUMNS_TITLE + LINE_SEPARATOR + "apple,50" + LINE_SEPARATOR + "banana,60"
                + LINE_SEPARATOR + "orange,70";
        String actual = reportService.getReport();

        assertEquals(expected, actual, "There's an error during creating the report");
    }

    @Test
    void getReportFromSet2_Ok() {
        Storage.FRUITS.put("orange", 70);
        Storage.FRUITS.put("apple", 50);
        Storage.FRUITS.put("banana", 60);
        String expected = COLUMNS_TITLE + LINE_SEPARATOR + "apple,50" + LINE_SEPARATOR + "banana,60"
                + LINE_SEPARATOR + "orange,70";
        String actual = reportService.getReport();

        assertEquals(expected, actual, "There's an error during creating the report");
    }

    @Test
    void getReportFromSet3_Ok() {
        Storage.FRUITS.put("orange", 10);
        Storage.FRUITS.put("banana", 20);
        Storage.FRUITS.put("newfruit", 30);
        Storage.FRUITS.put("apple", 40);
        String expected = COLUMNS_TITLE + LINE_SEPARATOR + "apple,40" + LINE_SEPARATOR + "banana,20"
                + LINE_SEPARATOR + "newfruit,30" + LINE_SEPARATOR + "orange,10";
        String actual = reportService.getReport();

        assertEquals(expected, actual, "There's an error during creating the report");
    }

    @Test
    void getReportFromSet3_notOk() {
        Storage.FRUITS.put("orange", 10);
        Storage.FRUITS.put("orange", 100);
        Storage.FRUITS.put("banana", 20);
        Storage.FRUITS.put("newfruit", 30);
        Storage.FRUITS.put("apple", 40);
        String expected = COLUMNS_TITLE + LINE_SEPARATOR + "apple,40" + LINE_SEPARATOR + "banana,20"
                + LINE_SEPARATOR + "newfruit,30" + LINE_SEPARATOR + "orange,10";
        String actual = reportService.getReport();

        assertNotEquals(expected, actual, "There's an error during creating the report");
    }

    @Test
    void getReportFromSetWithNegatives_Ok() {
        Storage.FRUITS.put("orange", -10);
        Storage.FRUITS.put("banana", -20);
        Storage.FRUITS.put("newfruit", -30);
        String expected = COLUMNS_TITLE + LINE_SEPARATOR + "banana,-20" + LINE_SEPARATOR
                + "newfruit,-30" + LINE_SEPARATOR + "orange,-10";
        String actual = reportService.getReport();

        assertEquals(expected, actual, "There's an error during creating the report");
    }

    @Test
    void getReportFromEmptyStorage_notOk() {
        assertThrows(RuntimeException.class, () -> reportService.getReport());
    }

    @AfterEach
    void doAfterEachTest() {
        Storage.FRUITS.clear();
    }
}
