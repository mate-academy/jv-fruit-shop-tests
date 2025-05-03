package strategy;

import static org.junit.Assert.assertEquals;

import database.StorageFruits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shop.Fruit;
import shop.FruitShopOperation;

public class PurchaseHandlerTest {
    private TypeHandler typeHandler;

    @Before
    public void setUp() {
        StorageFruits.storageFruits.clear();
        typeHandler = new PurchaseHandler();
    }

    @After
    public void tearDown() {
        StorageFruits.storageFruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void purchaseEmptyStorage_NotOk() {
        typeHandler.countAmount(new FruitShopOperation("p", new Fruit("apple"), 2));
    }

    @Test
    public void purchaseNonEmptyStorage_Ok() {
        StorageFruits.storageFruits.put(new Fruit("banana"),15);
        typeHandler.countAmount(new FruitShopOperation("p", new Fruit("banana"), 5));
        Integer expected = 10;
        Integer actual = StorageFruits.storageFruits.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }
}
