package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String HEADER = "fruit,quantity";
    private FruitsDao fruitsDao = new FruitsDaoImpl();
    private ReportCreator reportCreator = new ReportCreatorImpl(HEADER, fruitsDao);
    private String firstFruit = "banana";
    private String secondFruit = "apple";
    private int firstFruitQuantity = 50;
    private int secondFruitQuantity = 40;

    @Before
    public void setUp() {
        Storage.fruits.put(firstFruit, firstFruitQuantity);
        Storage.fruits.put(secondFruit, secondFruitQuantity);
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
        reportWithHeaderNullValue.create(Storage.fruits);
    }

    @Test(expected = RuntimeException.class)
    public void constructor_fruitsDaoIsNull_NotOk() {
        ReportCreator reportWithFruitsDaoNullValue = new ReportCreatorImpl(HEADER, null);
        reportWithFruitsDaoNullValue.create(Storage.fruits);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_InputDataIsNull_NotOk() {
        reportCreator.create(null);
    }
}
