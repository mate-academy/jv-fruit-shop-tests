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

public class ReturnHandlerTest {
    private TransactionHandler returnHandler;
    private Fruit fruit;

    @Before
    public void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        returnHandler = new ReturnHandler(fruitDao);
        fruit = new Fruit("banana");
        Storage.fruits.clear();
        Storage.fruits.put(fruit, 15);
    }

    @Test
    public void return_valid_ok() {
        Integer expected = Storage.fruits.get(fruit) + 5;
        returnHandler.apply(new Transaction(Transaction.Operation.RETURN,
                fruit, 5));
        Assert.assertEquals(expected, Storage.fruits.get(fruit));
    }
}
