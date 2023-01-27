package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportService reportService = new ReportServiceImpl();

    @Test
    public void createReport_Ok() {
        FruitStorage.storage.put("banana", 152);
        FruitStorage.storage.put("apple", 90);
        String actualResult = reportService.createReport();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createReport_EmptyData_Ok() {
        String actualResult = reportService.createReport();
        String expectedResult = "fruit,quantity" + System.lineSeparator();
        assertEquals(expectedResult, actualResult);
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
