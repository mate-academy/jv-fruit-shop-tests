package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static final String HEADER = "fruit,quantity";

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void afterEach() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void getReport_isOk() {
        FruitStorage.fruitStorage.put("lemon", 20);
        FruitStorage.fruitStorage.put("apple", 50);
        FruitStorage.fruitStorage.put("banana", 5);
        String actual = reportService.getReport(FruitStorage.fruitStorage);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,5" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "lemon,20";
        assertEquals(actual, expected);
    }

    @Test
    public void createReport_emptyStorage_isOk() {
        String actualResult = reportService.getReport(FruitStorage.fruitStorage);
        assertEquals(actualResult, HEADER);
    }
}
