package core.basesyntax.services;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitCalculationService;
import core.basesyntax.service.impl.FruitCalculationServiceImpl;
import core.basesyntax.strategy.CalculatorStrategy;
import core.basesyntax.strategy.TypeCalculatorStrategy;
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
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitCalculatorTest {
    private static final List<FruitTransaction> TRANSACTIONS_NOT_VALID_DATA = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "mango", 100),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "mango", 60),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 120)
    );
    private static final List<FruitTransaction> TRANSACTIONS_VALID_DATA = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "mango", 100),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 200),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "mango", 60),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 120),
            new FruitTransaction(FruitTransaction.Operation.RETURN, "mango", 60)
    );
    private static final List<TypeCalculatorStrategy> strategies = List.of(
            new SupplyCalculatorImpl(),
            new ReturnCalculatorImpl(),
            new PurchaseCalculatorImpl(),
            new BalanceCalculatorImpl()
    );
    private static CalculatorStrategy calculatorStrategy;
    private static FruitCalculationService fruitCalculationService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        calculatorStrategy = new CalculatorStrategyImpl(strategies);
        fruitCalculationService
                = new FruitCalculationServiceImpl(calculatorStrategy);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void addToStorage_notValidPurchaseTransaction_Ok() {
        fruitCalculationService.addToStorage(TRANSACTIONS_NOT_VALID_DATA);
        Map<String, Integer> actual = Storage.storage;
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 100);
        expected.put("mango", 40);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addToStorage_validPurchaseTransaction_Ok() {
        fruitCalculationService.addToStorage(TRANSACTIONS_VALID_DATA);
        Map<String, Integer> actual = Storage.storage;
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 80);
        expected.put("mango", 100);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void addToStorage_emptyList_notOk() {
        fruitCalculationService.addToStorage(List.of());
    }

    @Test(expected = NullPointerException.class)
    public void addToStorage_null_notOk() {
        fruitCalculationService.addToStorage(null);
    }
}
