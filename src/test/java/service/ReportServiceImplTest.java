package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReportServiceImplTest {
    private static Map<String, Integer> report;
    private static List<String> expectedStrReport;
    private static ReportService reportService;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
        report = new HashMap<>();
        expectedStrReport = new ArrayList<>();
    }

    @After
    public void tearDown() {
        report.clear();
        expectedStrReport.clear();
    }

    @Test
    public void makeReport_Ok() {
        report.put("banana", 100);
        report.put("apple", 50);
        expectedStrReport.add("fruit,quantity");
        expectedStrReport.add("banana,100");
        expectedStrReport.add("apple,50");
        List<String> actualStrReport = reportService.makeReport(report);
        Assert.assertEquals(expectedStrReport, actualStrReport);
    }

    @Test
    public void makeEmptyReport_Ok() {
        expectedStrReport.add("fruit,quantity");
        List<String> actualStrReport = reportService.makeReport(report);
        Assert.assertEquals(expectedStrReport, actualStrReport);
    }

    @Test
    public void makeNullReport_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Report is null");
        reportService.makeReport(null);
    }
}
