package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
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
    private List<FruitTransaction> fruitTransactions = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
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

    @BeforeClass
    public static void beforeClass() {
        dataTransactionService = new DataTransactionServiceImpl();

    }

    @Test
    public void parseData_validData_ok() {
        dataTransactionService.parseData(fruitTransactions);
        FruitDao fruitDao = new FruitDaoImpl();
        Map<String, Integer> actual = fruitDao.getAll();
        Map<String, Integer> expected = Map.of("banana", 152,"apple", 90);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseData_emptyList_ok() {
        fruitTransactions.clear();
        dataTransactionService.parseData(fruitTransactions);
    }

    @Test
    public void parseData_emptyFruit_notOk() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                null, 23));
        dataTransactionService.parseData(fruitTransactions);
    }

    @Test
    public void parseData_negativeQuantity_notOk() {
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
        FruitStorage.storageFruits.clear();
        fruitTransactions.clear();
    }
}
