package core.basesyntax.serviceimpl;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService();
    }

    @Test
    void createReport_Ok() {
        Storage.DB.put("banana",10);
        String actual = reportService.createReport();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,10";
        Assertions.assertEquals(expected,actual);
    }
}
