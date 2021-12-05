package strategy;

import static org.junit.Assert.assertEquals;

import database.StorageFruits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shop.Fruit;
import shop.FruitShopOperation;

public class ReturnHandlerTest {
    private TypeHandler typeHandler;

    @Before
    public void setUp() {
        StorageFruits.storageFruits.clear();
        typeHandler = new ReturnHandler();
    }

    @After
    public void tearDown() {
        StorageFruits.storageFruits.clear();
    }

    @Test
    public void returnEmptyStorage_Ok() {
        typeHandler.countAmount(new FruitShopOperation("b", new Fruit("apple"), 20));
        Integer expected = 20;
        Integer actual = StorageFruits.storageFruits.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void returnNonEmptyStorage_Ok() {
        StorageFruits.storageFruits.put(new Fruit("banana"), 10);
        typeHandler.countAmount(new FruitShopOperation("b", new Fruit("banana"), 13));
        Integer expected = 23;
        Integer actual = StorageFruits.storageFruits.get(new Fruit("banana"));
        assertEquals(expected, actual);
    }
}
