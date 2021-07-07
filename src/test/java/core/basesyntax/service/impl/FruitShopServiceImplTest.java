package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitShopService;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void beforeClass() {
        fruitShopService = new FruitShopServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void fruitShopServiceUsualState_ok() {
        Storage.fruitStorage.put(new Fruit("apple"), 15);
        String expected = new StringBuilder()
                .append("fruit").append(",")
                .append("quantity").append(System.lineSeparator())
                .append("apple").append(",")
                .append("15").append(System.lineSeparator())
                .toString();
        String actual = fruitShopService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fruitShopServiceEmptyStorage_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = fruitShopService.createReport();
        Assert.assertEquals(expected, actual);
    }
}
