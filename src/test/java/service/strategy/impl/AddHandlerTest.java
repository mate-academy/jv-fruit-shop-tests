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

public class AddHandlerTest {
    private TransactionHandler addHandler;
    private Fruit fruit;

    @Before
    public void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        addHandler = new AddHandler(fruitDao);
        fruit = new Fruit("banana");
        Storage.fruits.clear();
        Storage.fruits.put(fruit, 15);
    }

    @Test
    public void return_valid_ok() {
        Integer expected = Storage.fruits.get(fruit) + 5;
        addHandler.execute(new Transaction(Transaction.Operation.RETURN,
                fruit, 5));
        Assert.assertEquals(expected, Storage.fruits.get(fruit));
    }
}
