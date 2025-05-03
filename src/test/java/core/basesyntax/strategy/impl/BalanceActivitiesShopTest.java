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

public class BalanceActivitiesShopTest {
    private static ActivitiesShop activitiesShop;
    private static Fruit fruit;
    private FruitOperation fruitOperation;

    @BeforeClass
    public static void beforeClass() throws Exception {
        activitiesShop = new BalanceActivitiesShop();
    }

    @Before
    public void setUp() throws Exception {
        fruit = new Fruit("apple");
        fruitOperation = new FruitOperation(TypeActivity.typeGiven("b"),
                new Fruit("apple"), 30);
    }

    @Test
    public void getBalance_Ok() {
        int expect = 30;
        activitiesShop.calculate(fruitOperation);
        int actual = Storage.fruits.get(fruit);
        assertEquals(expect, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
