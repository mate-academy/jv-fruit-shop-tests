package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

public class ReportCreateServiceTest {
    private ReportCreateService reportCreateService;
    private Fruit apple;
    private Fruit banana;
    private Fruit strawberry;

    @Before
    public void setUp() {
        reportCreateService =
                new ReportCreateService(new FruitDaoImpl());
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        strawberry = new Fruit("strawberry");
        Storage.getFruitDataBase().entrySet().clear();
    }

    @Test
    public void createReport_reportNormalData_OK() {
        Storage.getFruitDataBase().put(apple, 44);
        Storage.getFruitDataBase().put(banana, 25);
        Storage.getFruitDataBase().put(strawberry, 11);
        String actual = reportCreateService.createReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,25" + System.lineSeparator()
                + "apple,44" + System.lineSeparator()
                + "strawberry,11" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_reportWithNotNormalData_Ok() {
        Storage.getFruitDataBase().put(apple, 23);
        Storage.getFruitDataBase().put(banana, 11);
        Storage.getFruitDataBase().put(null, -10);
        String actual = reportCreateService.createReport();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "null,-10" + System.lineSeparator()
                + "banana,11" + System.lineSeparator()
                + "apple,23" + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
