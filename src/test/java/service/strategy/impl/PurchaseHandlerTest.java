package service.strategy.impl;

import dao.FruitDao;
import dao.impl.FruitDaoImpl;
import database.Storage;
import model.Fruit;
import model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.strategy.TransactionHandler;

public class PurchaseHandlerTest {
    private TransactionHandler purchaseHandler;
    private FruitDao fruitDao;
    private Fruit fruit;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        purchaseHandler = new PurchaseHandler(fruitDao);
        fruit = new Fruit("banana");
        Storage.fruits.clear();
        Storage.fruits.put(fruit, 15);
    }

    @Test
    public void purchase_valid_ok() {
        Integer expected = Storage.fruits.get(fruit) - 10;
        purchaseHandler.apply(new Transaction(Transaction.Operation.PURCHASE,
                fruit, 10));
        Assert.assertEquals(expected, Storage.fruits.get(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void purchase_invalid_notOk() {
        purchaseHandler.apply(new Transaction(Transaction.Operation.PURCHASE,
                fruit, 20));
    }
}
