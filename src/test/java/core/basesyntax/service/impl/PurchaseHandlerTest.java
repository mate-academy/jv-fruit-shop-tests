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
    private Map<Fruit,Integer> expectedMap;

    @Before
    public void setUp() {
        expectedMap = new HashMap<>();
        Storage.fruits.clear();
    }

    @Test
    public void purchase_CorrectData_Ok() {
        expectedMap.put(new Fruit("banana"),15);
        Storage.fruits.put(new Fruit("banana"),20);
        purchaseHandler.apply(new Transaction("p","banana",5));
        Assert.assertEquals(expectedMap, Storage.fruits);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_QuantityMoreThanBalance_NotOk() {
        Storage.fruits.put(new Fruit("banana"),5);
        purchaseHandler.apply(new Transaction("p","banana",10));
    }
}
