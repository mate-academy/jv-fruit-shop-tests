package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private static ReportService reportService;

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
        String expected = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator()).append("banana,5")
                .append(System.lineSeparator()).append("apple,50")
                .append(System.lineSeparator()).append("lemon,20").toString();
        assertEquals(actual, expected);
    }

    @Test
    public void createReport_emptyStorage_isOk() {
        String actualResult = reportService.getReport(FruitStorage.fruitStorage);
        assertEquals(actualResult, HEADER);
    }
}
