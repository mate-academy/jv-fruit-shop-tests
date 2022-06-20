package service.impl;

import static org.junit.Assert.assertEquals;

import dao.FruitDao;
import dao.FruitDaoImpl;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.OperationHandler;
import strategy.AddOperationHandler;
import strategy.SetBalanceOperationHandler;
import strategy.SubtractOperationHandler;

public class OperationHandlerTest {
    private static FruitDao fruitDao;
    private static Map<String, Integer> storage;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        storage = new HashMap<>();
        fruitDao = new FruitDaoImpl(storage);
    }

    @Test
    public void doTransaction_supplyOperationsWorkRight_ok() {
        String message = "expected quantity and actual are different";
        OperationHandler operationHandler = new AddOperationHandler(fruitDao);
        operationHandler.doTransaction(new FruitTransaction("s", "watermelon", 10));
        assertEquals(message, 10, fruitDao.get("watermelon").intValue());
    }

    @Test
    public void doTransaction_returnOperationsWorkRight_ok() {
        String message = "expected quantity and actual are different";
        storage.put("pineapple", 50);
        OperationHandler operationHandler = new AddOperationHandler(fruitDao);
        operationHandler.doTransaction(new FruitTransaction("r", "pineapple", 20));
        assertEquals(message, 70, fruitDao.get("pineapple").intValue());
    }

    @Test
    public void doTransaction_purchaseOperationQuantityFruitsEnough_ok() {
        String message = "expected quantity and actual are different";
        storage.put("banana", 50);
        OperationHandler subtractOperationHandler = new SubtractOperationHandler(fruitDao);
        subtractOperationHandler.doTransaction(new FruitTransaction("p", "banana", 10));
        assertEquals(message, 40, fruitDao.get("banana").intValue());
    }

    @Test
    public void doTransaction_purchaseOperationQuantityFruitsNotEnough_notOk() {
        OperationHandler subtractOperationHandler = new SubtractOperationHandler(fruitDao);
        exception.expect(RuntimeException.class);
        exception.expectMessage("you have no fruit's quantity available for PURCHASE operation");
        subtractOperationHandler.doTransaction(new FruitTransaction("p", "potato", 10));
    }

    @Test
    public void doTransaction_balanceOperationWhenSomeQuantityIsPresent_ok() {
        String message = "something wrong";
        storage.put("fungi", 150);
        OperationHandler balanceOperationHandler = new SetBalanceOperationHandler(fruitDao);
        balanceOperationHandler.doTransaction(new FruitTransaction("s", "fungi", 50));
        assertEquals(message, 50, fruitDao.get("fungi").intValue());
    }

    @After
    public void clearStorage() {
        storage.clear();
    }
}
