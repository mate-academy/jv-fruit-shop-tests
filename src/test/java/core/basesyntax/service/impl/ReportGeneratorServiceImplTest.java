package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorServiceImplTest {
    private ReportGeneratorService reportGeneratorService;

    @Before
    public void setUp() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
    }

    @Test
    public void generateReport_Ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();

        Storage.fruitMap.put("banana", 152);
        Storage.fruitMap.put("apple", 90);

        String generatedReport = reportGeneratorService.generateReport();

        Assert.assertEquals(expectedReport, generatedReport);
    }

    @Test
    public void generateReportWithEmptyStorage_Ok() {
        String generatedReport = reportGeneratorService.generateReport();
        String expected = "fruit,quantity" + System.lineSeparator();
        Assert.assertEquals(expected, generatedReport);
    }

    @After
    public void tearDown() {
        Storage.fruitMap.clear();
    }
}
