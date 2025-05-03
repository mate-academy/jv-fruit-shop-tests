package core.basesyntax.service;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;

    @Before
    public void setUp() {
        fruitShopService = new FruitShopServiceImpl();
    }

    @Test
    public void addToStorage_Ok() {
        fruitShopService.add("banana", 20);
        Assert.assertTrue(Storage.storage.containsKey("banana"));
        Assert.assertTrue(Storage.storage.containsValue(20));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
