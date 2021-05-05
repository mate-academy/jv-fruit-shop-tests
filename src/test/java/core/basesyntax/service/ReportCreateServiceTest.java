package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

public class ReportCreateServiceTest {
    private static final ReportCreateService reportCreateService =
            new ReportCreateService(new FruitDaoImpl());
    private final Fruit apple = new Fruit("apple");
    private final Fruit banana = new Fruit("banana");
    private final Fruit strawberry = new Fruit("strawberry");

    @Before
    public void setUp() {
        Storage.getFruitDataBase().entrySet().clear();
    }

    @Test
    void createReport_reportNormalData_OK() {
        Storage.getFruitDataBase().put(apple, 44);
        Storage.getFruitDataBase().put(banana, 25);
        Storage.getFruitDataBase().put(strawberry, 11);
        String actual = reportCreateService.createReport();
        String expected = "fruit,quantity\r\n"
                + "banana,25\r\n"
                + "apple,44\r\n"
                + "strawberry,11\r\n";
        assertEquals(expected, actual);
    }

    @Test
    void createReport_reportWithNotNormalData_Ok() {
        Storage.getFruitDataBase().put(apple, 23);
        Storage.getFruitDataBase().put(banana, 11);
        Storage.getFruitDataBase().put(null, -10);
        String actual = reportCreateService.createReport();
        String expected = "fruit,quantity\r\n"
                + "null,-10\r\n"
                + "banana,11\r\n"
                + "apple,23\r\n";
        assertEquals(expected, actual);
    }
}
