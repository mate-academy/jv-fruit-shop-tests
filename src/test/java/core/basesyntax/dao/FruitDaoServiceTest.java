package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.serviceimpl.FruitDaoServiceImpl;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoServiceTest {
    private static FruitDaoServiceImpl fruitDaoService;
    private static Storage storage;
    private static List<FruitTransaction> transactions;

    @Before
    public void setUp() {
        fruitDaoService = new FruitDaoServiceImpl();
        transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple",10));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "bananas",120));

    }

    @After
    public void afterEachTest() {
        Storage.fruitTransactions.clear();
    }

    @Test
    public void addTest_Data_OK() {
        List<FruitTransaction> expected = transactions;
        fruitDaoService.add(transactions);
        List<FruitTransaction> actual = Storage.fruitTransactions;
        assertEquals(expected, actual);
    }

    @Test
    public void addTest_EmptyData_OK() {
        List<FruitTransaction> expected = transactions;
        fruitDaoService.add(transactions);
        List<FruitTransaction> actual = Storage.fruitTransactions;
        assertEquals(expected, actual);
    }

    @Test
    public void getTest_Data_OK() {
        List<FruitTransaction> expected = transactions;
        Storage.fruitTransactions.addAll(transactions);
        List<FruitTransaction> actual = fruitDaoService.get();
        assertEquals(expected, actual);
    }

    @Test
    public void getTest_EmptyData_OK() {
        List<FruitTransaction> expected = transactions;
        Storage.fruitTransactions.addAll(transactions);
        List<FruitTransaction> actual = fruitDaoService.get();
        assertEquals(expected, actual);
    }
}
