package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.dao.BalanceOperationHandlerImpl;
import core.basesyntax.dao.OperationHandler;
import core.basesyntax.dao.PurchaseOperationHandlerImpl;
import core.basesyntax.dao.ReturnOperationHandlerImpl;
import core.basesyntax.dao.SupplyOperationHandlerImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PopulatingDbServiceImplTest {
    public static final String BALANCE = "b";
    public static final String SUPPLY = "s";
    public static final String RETURN = "r";
    public static final String PURCHASE = "p";
    private static final Map<String, OperationHandler> operationTypes = new HashMap<>();
    private static PopulatingDbServiceImpl populatingDb;
    private static List<FruitTransaction> invalidTransactions;
    private static List<FruitTransaction> balanceTransactions;
    private static List<FruitTransaction> purchaseTransactions;
    private static List<FruitTransaction> purchaseTransactionsInvalidFruits;
    private static List<FruitTransaction> purchaseTransactionsInvalidCount;
    private static List<FruitTransaction> supplyTransactions;
    private static List<FruitTransaction> returnTransactions;

    @BeforeClass
    public static void beforeClass() {
        operationTypes.put(BALANCE, new BalanceOperationHandlerImpl());
        operationTypes.put(SUPPLY, new SupplyOperationHandlerImpl());
        operationTypes.put(RETURN, new ReturnOperationHandlerImpl());
        operationTypes.put(PURCHASE, new PurchaseOperationHandlerImpl());
        populatingDb = new PopulatingDbServiceImpl(operationTypes);
        invalidTransactions = new ArrayList<>(List.of(
                new FruitTransaction("j", "banana", 10),
                new FruitTransaction("l", "apple", 10)));
        balanceTransactions = new ArrayList<>(List.of(
                new FruitTransaction("b", "banana", 10),
                new FruitTransaction("b", "apple", 10)));
        purchaseTransactions = new ArrayList<>(List.of(
                new FruitTransaction("p", "banana", 10),
                new FruitTransaction("p", "apple", 10)));
        purchaseTransactionsInvalidFruits = new ArrayList<>(List.of(
                new FruitTransaction("p", "orange", 10),
                new FruitTransaction("p", "pineapple", 10)));
        purchaseTransactionsInvalidCount = new ArrayList<>(List.of(
                new FruitTransaction("p", "banana", 100),
                new FruitTransaction("p", "apple", 100)));
        supplyTransactions = new ArrayList<>(List.of(
                new FruitTransaction("s", "banana", 10),
                new FruitTransaction("s", "apple", 10)));
        returnTransactions = new ArrayList<>(List.of(
                new FruitTransaction("r", "banana", 10),
                new FruitTransaction("r", "apple", 10)));
        Storage.fruitStorage.put("banana", 0);
        Storage.fruitStorage.put("apple", 0);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void populatingDb_InvalidOperation() {
        populatingDb.prepareDB(invalidTransactions);
    }

    @Test
    public void populatingDb_BalanceOperation() {
        Storage.fruitStorage.clear();
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        expected.put("apple", 10);
        Map<String, Integer> initialState = new HashMap<>(Storage.fruitStorage);
        populatingDb.prepareDB(balanceTransactions);
        assertNotEquals(initialState.size(), Storage.fruitStorage.size());
        assertEquals(expected, Storage.fruitStorage);
    }

    @Test(expected = RuntimeException.class)
    public void populatingDb_PurchaseOperation() {
        Storage.fruitStorage.put("banana", 30);
        Storage.fruitStorage.put("apple", 30);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 20);
        expected.put("apple", 20);
        populatingDb.prepareDB(purchaseTransactions);
        assertEquals(expected, Storage.fruitStorage);
        populatingDb.prepareDB(purchaseTransactionsInvalidFruits);
        populatingDb.prepareDB(purchaseTransactionsInvalidCount);
    }

    @Test
    public void populatingDb_ReturnOperation() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        expected.put("apple", 10);
        populatingDb.prepareDB(returnTransactions);
        assertEquals(expected, Storage.fruitStorage);
    }

    @Test
    public void populatingDb_SupplyOperation() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        expected.put("apple", 10);
        populatingDb.prepareDB(supplyTransactions);
        assertEquals(expected, Storage.fruitStorage);
    }
}
