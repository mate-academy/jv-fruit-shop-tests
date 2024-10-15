package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportService;
    private Map<String, Integer> inventoryData;

    @BeforeEach
    public void setUp() {
        reportService = new ReportGeneratorImpl();
        inventoryData = new HashMap<>();
    }

    @Test
    public void returnsCorrectFormat_getReport_Ok() {
        inventoryData.put("apple", 10);
        inventoryData.put("banana", 5);
        inventoryData.put("orange", 20);
        String expectedReport = "fruit,quantity\n"
                + "banana,5\n"
                + "orange,20\n"
                + "apple,10";

        String actualReport = reportService.getReport(inventoryData);
        Assertions.assertEquals(expectedReport, actualReport);
    }

    @Test
    public void returnsHeaderOnly_getReport_Ok() {
        String expectedReport = "fruit,quantity";
        String actualReport = reportService.getReport(inventoryData);
        Assertions.assertEquals(expectedReport, actualReport);
    }
}
