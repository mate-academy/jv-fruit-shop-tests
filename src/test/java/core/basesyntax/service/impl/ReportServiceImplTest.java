package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.TransactionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String EXCEPTION_MESSAGE
            = TransactionException.class.toString();
    private static final String HEADER = "fruit,quantity";
    private static final String SEPARATOR = System.lineSeparator();
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void report_emptyStorage_notOk() {
        Assertions.assertThrows(TransactionException.class, () -> reportService.createReport(),
                String.format("%s should throw for empty storage", EXCEPTION_MESSAGE));
    }

    @Test
    void report_isNotEmptyStorage_Ok() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 10);
        Assertions.assertNotEquals(HEADER, reportService.createReport());
    }

    @Test
    void report_createCorrectReport_Ok() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 10);
        StringBuilder reportBuilder = new StringBuilder();
        String expected = reportBuilder.append(HEADER)
                .append(SEPARATOR)
                .append("banana,20")
                .append(SEPARATOR)
                .append("apple,10").toString();
        String actual = reportService.createReport();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void report_createCorrectReport_notOk() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 10);
        StringBuilder reportBuilder = new StringBuilder();
        String expected = reportBuilder.append(HEADER)
                .append(SEPARATOR)
                .append("banana,200")
                .append(SEPARATOR)
                .append("apple,100").toString();
        String actual = reportService.createReport();
        Assertions.assertNotEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
