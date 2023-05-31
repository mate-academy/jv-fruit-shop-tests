package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String HEADER = "fruit,quantity";
    private static FruitsDao fruitsDao;
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void beforeClass() {
        fruitsDao = new FruitsDaoImpl();
        reportCreator = new ReportCreatorImpl(HEADER, fruitsDao);
        Storage.fruits.put("banana", 50);
        Storage.fruits.put("apple", 40);
    }

    @Test
    public void create_createReport_Ok() {
        String actual = reportCreator.create(Storage.fruits);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "apple,40";
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void constructor_headerIsNull_NotOk() {
        ReportCreator reportWithHeaderNullValue = new ReportCreatorImpl(null, fruitsDao);
    }

    @Test(expected = RuntimeException.class)
    public void constructor_fruitsDaoIsNull_NotOk() {
        ReportCreator reportWithFruitsDaoNullValue = new ReportCreatorImpl(HEADER, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_InputDataIsNull_NotOk() {
        reportCreator.create(null);
    }
}
