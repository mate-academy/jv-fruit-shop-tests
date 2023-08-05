package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataTransactionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataTransactionServiceTest {
    private static DataTransactionService dataTransactionService;
    private static FruitDao fruitDao;
    private List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void init() {
        fruitDao = new FruitDaoImpl();
        dataTransactionService = new DataTransactionServiceImpl();
    }

    @Before
    public void setUp() {
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 100));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 13));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 5));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 50));
    }

    @Test
    public void parseData_validData_ok() {
        dataTransactionService.parseData(fruitTransactions);
        Map<String, Integer> actual = fruitDao.getAll();
        Map<String, Integer> expected = Map.of("banana", 152,"apple", 90);
        Assert.assertEquals("Expected " + expected + " for valid data, but was "
                + actual, expected, actual);
    }

    @Test
    public void parseData_emptyList_ok() {
        fruitTransactions.clear();
        dataTransactionService.parseData(fruitTransactions);
    }

    @Test
    public void parseData_emptyFruit_ok() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                null, 23));
        dataTransactionService.parseData(fruitTransactions);
    }

    @Test
    public void parseData_negativeQuantity_ok() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", -15));
        dataTransactionService.parseData(fruitTransactions);
    }

    @Test(expected = NullPointerException.class)
    public void parseData_emptyOperation_ok() {
        fruitTransactions.add(new FruitTransaction(null,
                "banana", 23));
        dataTransactionService.parseData(fruitTransactions);
    }

    @After
    public void tearDown() {
        fruitDao.getAll().clear();
        fruitTransactions.clear();
    }
}
