package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String INDENTION = "\t";
    private static final String SEPARATOR = ",";
    private static final String TITLE = "fruits,quantity";
    private final ReportService reportService = new ReportServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void createReport_emptyStorage_Ok() {
        String expected = INDENTION + TITLE;
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_oneElementInStorage_Ok() {
        Storage.fruits.put("banana", 10);
        String expected = new StringBuilder()
                .append(INDENTION)
                .append(TITLE)
                .append(System.lineSeparator())
                .append(INDENTION)
                .append("banana")
                .append(SEPARATOR)
                .append(Storage.fruits.get("banana")).toString();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
