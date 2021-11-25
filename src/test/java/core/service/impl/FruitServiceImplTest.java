package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.dao.FruitDao;
import core.dao.FruitDaoImpl;
import core.db.Storage;
import core.model.Fruit;
import core.model.TransactionDto;
import core.strategy.Operation;
import core.strategy.OperationHandler;
import core.strategy.OperationStrategy;
import core.strategy.impl.AddOperationImpl;
import core.strategy.impl.BalanceOperationImpl;
import core.strategy.impl.OperationStrategyImpl;
import core.strategy.impl.PurchaseOperationImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitDao fruitDao;
    private static final Map<Operation, OperationHandler> operation = new HashMap<>();
    private static OperationStrategy operationStrategy;
    private static List<TransactionDto> transactions;
    private static FruitServiceImpl fruitStore;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationStrategy = new OperationStrategyImpl(operation);
        transactions = new ArrayList<>();
        fruitStore = new FruitServiceImpl(fruitDao, operationStrategy);
        operation.put(Operation.BALANCE, new BalanceOperationImpl(fruitDao));
        operation.put(Operation.PURCHASE, new PurchaseOperationImpl(fruitDao));
        operation.put(Operation.RETURN, new AddOperationImpl(fruitDao));
        operation.put(Operation.SUPPLY, new AddOperationImpl(fruitDao));
    }

    @Before
    public void beforeEachTest() {
        transactions.add(new TransactionDto("b", "banana", 65));
        transactions.add(new TransactionDto("s", "banana", 5));
        transactions.add(new TransactionDto("r", "banana", 5));
        transactions.add(new TransactionDto("p", "banana", 10));
    }

    @Test
    public void changeBalance_allActivity_ok() {
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 65));
        List<Fruit> actual = fruitStore.changeBalance(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void changeBalance_supply_ok() {
        transactions.add(new TransactionDto("s", "banana", 10));
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 75));
        List<Fruit> actual = fruitStore.changeBalance(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void changeBalance_return_ok() {
        transactions.add(new TransactionDto("r", "banana", 5));
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 70));
        List<Fruit> actual = fruitStore.changeBalance(transactions);
        assertEquals(expected, actual);
    }

    @Test
    public void changeBalance_purchase_ok() {
        transactions.add(new TransactionDto("p", "banana", 10));
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 55));
        List<Fruit> actual = fruitStore.changeBalance(transactions);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void changeBalance_purchaseMoreThanBalance_notOk() {
        transactions.add(new TransactionDto("p", "banana", 75));
        fruitStore.changeBalance(transactions);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
        transactions.clear();
    }
}
