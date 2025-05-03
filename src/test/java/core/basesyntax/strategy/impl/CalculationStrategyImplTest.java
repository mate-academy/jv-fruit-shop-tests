package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.CalculationStrategy;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CalculationStrategyImplTest {
    private static final List<FruitTransaction> TRANSACTIONS_VALID_DATA = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "pear", 100),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 200),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "pear", 60),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 120),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "pear", 60),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 200),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "pear", 200)
    );
    private static OperationHandler balance;
    private static CalculationStrategy calculationStrategy;

    @BeforeClass
    public static void beforeClass() {
        balance = new BalanceOperationHandler();
        calculationStrategy = new CalculationStrategyImpl();
    }

    @Before
    public void setUp() {
        balance.operate(TRANSACTIONS_VALID_DATA.get(0));
        balance.operate(TRANSACTIONS_VALID_DATA.get(1));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void calculate_purchaseStrategy_ok() {
        calculationStrategy.calculate(TRANSACTIONS_VALID_DATA.get(2));
        calculationStrategy.calculate(TRANSACTIONS_VALID_DATA.get(3));
        Map<String,Integer> expected = new HashMap<>();
        expected.put("pear", 40);
        expected.put("apple", 80);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test
    public void calculate_balanceStrategy_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("pear", 100);
        expected.put("apple", 200);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test
    public void calculate_returnStrategy_ok() {
        calculationStrategy.calculate(TRANSACTIONS_VALID_DATA.get(4));
        calculationStrategy.calculate(TRANSACTIONS_VALID_DATA.get(5));
        Map<String,Integer> expected = new HashMap<>();
        expected.put("pear", 160);
        expected.put("apple", 220);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test
    public void calculate_supplyStrategy_ok() {
        calculationStrategy.calculate(TRANSACTIONS_VALID_DATA.get(6));
        calculationStrategy.calculate(TRANSACTIONS_VALID_DATA.get(7));
        Map<String,Integer> expected = new HashMap<>();
        expected.put("pear", 300);
        expected.put("apple", 400);
        Assert.assertEquals(expected, Storage.storage);
    }
}
