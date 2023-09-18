package core.basesyntax.serviceimpl;

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
        String actual = reportService.createReport();
        String expected = "fruit,quantity";
        Assertions.assertEquals(expected,actual);
    }
}
