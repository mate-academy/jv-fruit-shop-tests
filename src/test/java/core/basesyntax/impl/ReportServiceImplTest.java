package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.servise.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReportWithValidData_ok() {
        StringBuilder expected = new StringBuilder("fruit, quantity")
                .append(System.lineSeparator())
                .append("banana,100")
                .append(System.lineSeparator())
                .append("apple,50")
                .append(System.lineSeparator());
        Storage.fruitStorage.put(new Fruit("banana"), 100);
        Storage.fruitStorage.put(new Fruit("apple"), 50);
        String actual = reportService.createReport();
        assertEquals(expected.toString(), actual);
    }

    @After
    public void cleanUp() {
        Storage.fruitStorage.clear();
    }
}
