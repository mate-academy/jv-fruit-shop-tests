package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceCalculatorImpl;
import core.basesyntax.strategy.impl.CalculatorStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseCalculatorImpl;
import core.basesyntax.strategy.impl.ReturnCalculatorImpl;
import core.basesyntax.strategy.impl.SupplyCalculatorImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorStrategyTest {
    private static final List<TypeCalculatorStrategy> strategies = List.of(
            new SupplyCalculatorImpl(),
            new ReturnCalculatorImpl(),
            new PurchaseCalculatorImpl(),
            new BalanceCalculatorImpl()
    );
    private static final List<FruitTransaction> TRANSACTIONS_VALID_DATA = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "mango", 100),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 200),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "mango", 60),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 120),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "mango", 60),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 200),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "mango", 200)
    );
    private final TypeCalculatorStrategy balance = new BalanceCalculatorImpl();
    private final CalculatorStrategy calculatorStrategy = new CalculatorStrategyImpl(strategies);

    @Before
    public void setUp() {
        balance.calculate(TRANSACTIONS_VALID_DATA.get(0));
        balance.calculate(TRANSACTIONS_VALID_DATA.get(1));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void calculate_purchaseStrategy_ok() {
        calculatorStrategy.calculate(TRANSACTIONS_VALID_DATA.get(2));
        calculatorStrategy.calculate(TRANSACTIONS_VALID_DATA.get(3));
        Map<String,Integer> expected = new HashMap<>();
        expected.put("mango", 40);
        expected.put("apple", 80);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test
    public void calculate_balanceStrategy_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("mango", 100);
        expected.put("apple", 200);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test
    public void calculate_returnStrategy_ok() {
        calculatorStrategy.calculate(TRANSACTIONS_VALID_DATA.get(4));
        calculatorStrategy.calculate(TRANSACTIONS_VALID_DATA.get(5));
        Map<String,Integer> expected = new HashMap<>();
        expected.put("mango", 160);
        expected.put("apple", 220);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test
    public void calculate_supplyStrategy_ok() {
        calculatorStrategy.calculate(TRANSACTIONS_VALID_DATA.get(6));
        calculatorStrategy.calculate(TRANSACTIONS_VALID_DATA.get(7));
        Map<String,Integer> expected = new HashMap<>();
        expected.put("mango", 300);
        expected.put("apple", 400);
        Assert.assertEquals(expected, Storage.storage);
    }
}
