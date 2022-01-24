package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.model.TypeActivity;
import core.basesyntax.strategy.ActivitiesShop;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseActivitiesShopTest {
    private static ActivitiesShop activitiesShop;
    private static FruitOperation fruitOperation;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() throws Exception {
        activitiesShop = new PurchaseActivitiesShop();
    }

    @Before
    public void setUp() throws Exception {
        fruitOperation = new FruitOperation(TypeActivity.typeGiven("p"),
                new Fruit("apple"), 60);
        fruit = new Fruit("apple");
    }

    @Test
    public void getPurchase_Ok() {
        Storage.fruits.put(fruit, 105);
        int expect = 45;
        activitiesShop.calculate(fruitOperation);
        int actual = Storage.fruits.get(fruit);
        assertEquals(expect, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseNull_NotOk() {
        activitiesShop.calculate(fruitOperation);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseNegative_NotOk() {
        Storage.fruits.put(fruit, 55);
        activitiesShop.calculate(fruitOperation);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
