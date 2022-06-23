package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Shop;
import core.basesyntax.service.ShopService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceImplTest {
    private static ShopService shopService;

    @BeforeClass
    public static void setUp() {
        shopService = new ShopServiceImpl();
    }

    @Test
    public void add_newFruit_Ok() {
        shopService.add("banana", 20);
        assertTrue(Shop.fruits.containsKey("banana"));
        Integer actual = Shop.fruits.get("banana");
        Integer expected = 20;
        assertEquals(expected, actual);
    }

    @Test
    public void add_sameFruit_Ok() {
        shopService.add("banana", 20);
        shopService.add("banana", 10);
        Integer expected = 30;
        Integer actual = Shop.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @After
    public void after() {
        Shop.fruits.clear();
    }
}
