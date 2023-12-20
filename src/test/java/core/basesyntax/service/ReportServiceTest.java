package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final String TITLE = "fruit,quantity";
    private static final String INDENTATION = "\t";
    private static final String SEPARATOR = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private ReportService reportService = new ReportServiceImpl();

    @Test
    void emptyDataBaseCreateReport_ok() {
        String expected = INDENTATION + TITLE;
        String actual = reportService.create();
        assertEquals(expected, actual);
    }

    @Test
    void checkNotEmptyReportMatching_ok() {
        storage.put("banana", 42);
        String expected = new StringBuilder()
                .append(INDENTATION).append(TITLE)
                .append(LINE_SEPARATOR).append(INDENTATION)
                .append("banana" + SEPARATOR + 42).toString();
        String actual = reportService.create();
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }
}
