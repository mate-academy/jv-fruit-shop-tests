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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorStrategyTest {
    private static final String TEST_FRUIT = "banana";
    private static List<FruitTransaction> transactions;
    private static Map<String, Integer> fruitsMap;
    private static TransactionProcessor strategy;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUpClass() {
        transactions = new ArrayList<>();
        fruitsMap = new HashMap<>();
        strategy = new TransactionProcessorStrategy();
        fruitTransaction = new FruitTransaction(Operation.BALANCE,
                TEST_FRUIT, 15);
    }

    @Before
    public void setUp() {
        fruitsMap.put(TEST_FRUIT, 25);
    }

    @After
    public void tearDown() {
        transactions.clear();
        fruitsMap.clear();
    }

    @Test
    public void process_addition_ok() {
        fruitTransaction.setOperation(Operation.SUPPLY);
        transactions.add(fruitTransaction);
        strategy.process(transactions, fruitsMap);
        final int expected = 40;
        int actual = fruitsMap.get(TEST_FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    public void process_additionIfReturn_ok() {
        fruitTransaction.setOperation(Operation.RETURN);
        transactions.add(fruitTransaction);
        strategy.process(transactions, fruitsMap);
        final int expected = 40;
        int actual = fruitsMap.get(TEST_FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    public void process_sellFromStorage_ok() {
        fruitTransaction.setOperation(Operation.PURCHASE);
        transactions.add(fruitTransaction);
        strategy.process(transactions, fruitsMap);
        final int expected = 10;
        int actual = fruitsMap.get(TEST_FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    public void process_initialFruits_ok() {
        fruitTransaction.setOperation(Operation.BALANCE);
        fruitsMap.clear();
        transactions.add(fruitTransaction);
        strategy.process(transactions, fruitsMap);
        final int expected = fruitTransaction.getQuantity();
        int actual = fruitsMap.get(TEST_FRUIT);
        assertEquals(expected, actual);
    }
}
