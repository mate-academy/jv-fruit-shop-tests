package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportService reportService = new ReportServiceImpl();

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void checkFirstLine_OK() {
        String expected = "fruit,quantity\n";
        String actualFirstLine = reportService.createReport();
        assertEquals(expected, actualFirstLine);
    }

    @Test
    public void createReport_OK() {
        Fruit fruit = new Fruit();
        fruit.setName("apple");
        fruit.setQuantity(20);
        Storage.fruits.add(fruit);
        String expected = "fruit,quantity\napple,20\n";
        String actualReport = reportService.createReport();
        assertEquals(expected, actualReport);
    }
}
