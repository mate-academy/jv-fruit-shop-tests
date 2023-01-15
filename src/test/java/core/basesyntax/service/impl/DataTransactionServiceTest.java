package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataTransactionService;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static final List<FruitTransaction> fruitTransactions = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        dataTransactionService = new DataTransactionServiceImpl();
        fruitDao = new FruitDaoImpl();
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

    @Before
    public void setUp() {

    }

    @Test
    public void updateStorage_Ok() {
        dataTransactionService.parseData(fruitTransactions);
        Map<String, Integer> actual = fruitDao.getAll();
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.storageFruits.clear();
    }
}
