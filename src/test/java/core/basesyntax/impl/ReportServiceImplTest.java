package core.basesyntax.impl;

import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void reportService_emptyData_Ok() {
        List<String> data = new ArrayList<>();
        List<String> expectedReport = new ArrayList<>();
        expectedReport.add("fruit,quantity");
        List<String> actualReport = reportService.report(data);
        Assertions.assertEquals(expectedReport, actualReport);
    }

    @Test
    void reportService_testReportWithData_ok() {
        List<String> data = Arrays.asList("b,apple,10", "s,apple,5", "p,apple,8");
        List<String> expectedReport = new ArrayList<>();
        expectedReport.add("fruit,quantity");
        expectedReport.addAll(data);
        List<String> actualReport = reportService.report(data);
        Assertions.assertEquals(expectedReport, actualReport);
    }
}
