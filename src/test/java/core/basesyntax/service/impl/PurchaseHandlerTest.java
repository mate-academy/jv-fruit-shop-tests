package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private final PurchaseHandler purchaseHandler = new PurchaseHandler(new FruitDaoImpl());

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void purchase_CorrectData_Ok() {
        Fruit fruit = new Fruit("banana");
        Storage.fruits.put(fruit,20);
        purchaseHandler.apply(new Transaction("p","banana",5));
        Assert.assertEquals(Integer.valueOf(15), Storage.fruits.get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void purchase_QuantityMoreThanBalance_NotOk() {
        Storage.fruits.put(new Fruit("banana"),5);
        purchaseHandler.apply(new Transaction("p","banana",10));
    }
}
