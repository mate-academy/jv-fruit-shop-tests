package service.strategy.impl;

import dao.FruitDao;
import dao.impl.FruitDaoImpl;
import database.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Fruit;
import model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.strategy.TransactionHandler;

public class BalanceHandlerTest {
    private TransactionHandler balanceHandler;
    private FruitDao fruitDao;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        balanceHandler = new BalanceHandler(fruitDao);
        Storage.fruits.clear();
    }

    @Test
    public void balance_handle_ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 52);
        expected.put(new Fruit("apple"), 99);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(Transaction.Operation.BALANCE,
                new Fruit("banana"), 52));
        transactions.add(new Transaction(Transaction.Operation.BALANCE,
                new Fruit("apple"), 99));
        transactions.forEach(t -> balanceHandler.execute(t));
        Assert.assertEquals(expected, Storage.fruits);
    }
}
