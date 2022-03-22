package core.basesyntax.service;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        FruitStorage.fruits.clear();
    }

    @Test(expected = NullPointerException.class)
    public void report_NullStorage_NotOk() {
        reportService.createOutput(null);
    }

    @Test
    public void report_EmptyStorage_Ok() {
        String actualOutput = reportService.createOutput(FruitStorage.fruits);
        String expectedOutput = "fruit,quantity" + System.lineSeparator();
        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void report_BasicStorage_Ok() {
        FruitStorage.fruits.add(new Fruit("apple", 14));
        FruitStorage.fruits.add(new Fruit("banana", 11));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity").append(System.lineSeparator());
        stringBuilder.append("apple,14").append(System.lineSeparator());
        stringBuilder.append("banana,11");

        String expectedOutput = stringBuilder.toString();
        String actualOutput = reportService.createOutput(FruitStorage.fruits);
        Assert.assertEquals(expectedOutput, actualOutput);
    }
}
