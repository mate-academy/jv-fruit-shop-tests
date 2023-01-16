package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionProcessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorStrategyTest {
    private static List<FruitTransaction> transactions;
    private static Map<String, Integer> fruits;
    private static TransactionProcessor strategy;

    @BeforeClass
    public static void setUp() {
        transactions = new ArrayList<>();
        fruits = new HashMap<>();
        strategy = new TransactionProcessorStrategy();
    }

    @After
    public void tearDown() {
        transactions.clear();
        fruits.clear();
    }

    @Test
    public void process_InitialFruits_ok() {
        transactions.add(new FruitTransaction(Operation.BALANCE, "banana", 15));
        strategy.process(transactions, fruits);
        int expected = 15;
        int actual = fruits.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void process_Addition_ok() {
        transactions.add(new FruitTransaction(Operation.SUPPLY,
                "banana", 15));
        fruits.put("banana", 25);
        strategy.process(transactions, fruits);
        int expected = 40;
        int actual = fruits.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void process_SellFromStorage_ok() {
        transactions.add(new FruitTransaction(Operation.PURCHASE,
                "banana", 10));
        fruits.put("banana", 30);
        strategy.process(transactions, fruits);
        int expected = 20;
        int actual = fruits.get("banana");
        assertEquals(expected, actual);
    }
}
