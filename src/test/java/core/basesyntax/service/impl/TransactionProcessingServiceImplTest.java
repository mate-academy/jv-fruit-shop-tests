package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessingService;
import core.basesyntax.strategy.OperationCalculator;
import core.basesyntax.strategy.OperationCalculatorStrategy;
import core.basesyntax.strategy.impl.BalanceCountStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseCountStrategyImpl;
import core.basesyntax.strategy.impl.ReturnCountStrategyImpl;
import core.basesyntax.strategy.impl.SupplyCountStrategyImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessingServiceImplTest {
    private static Map<FruitTransaction.Operation, OperationCalculator> countStrategyMap;
    private static OperationCalculatorStrategy operationStrategy;
    private static TransactionProcessingService transactionProcessingService;
    private static Storage storage;

    @BeforeClass
    public static void setUp() {
        countStrategyMap = new HashMap<>();
        countStrategyMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceCountStrategyImpl());
        countStrategyMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyCountStrategyImpl());
        countStrategyMap.put(
                FruitTransaction.Operation.PURCHASE, new PurchaseCountStrategyImpl());
        countStrategyMap.put(
                FruitTransaction.Operation.RETURN, new ReturnCountStrategyImpl());
        operationStrategy
                = new OperationCalculatorStrategy(countStrategyMap);
        transactionProcessingService
                = new TransactionProcessingServiceImpl(operationStrategy);
        storage = new Storage();
    }

    @AfterClass
    public static void cleanStorage() {
        storage.getFruitMap().clear();
    }

    @Test
    public void update_validInput_ok() {
        List<FruitTransaction> input = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 110),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 230),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 90),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 90),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 90)
        );
        transactionProcessingService.update(input);
        Assert.assertTrue("invalid result of updating fruitMap: "
                        + "expected value for: " + "banana"
                        + " = " + 120,
                storage.getFruitMap().containsKey("banana")
                        && storage.getFruitMap().get("banana") == 120);
        Assert.assertTrue("invalid result of updating fruitMap: "
                        + "expected value for: " + "apple"
                        + " = " + 320,
                storage.getFruitMap().containsKey("apple")
                        && storage.getFruitMap().get("apple") == 320);
    }

    @Test(expected = NullPointerException.class)
    public void update_nullInput_notOk() {
        transactionProcessingService.update(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void update_emptyInput_notOk() {
        transactionProcessingService.update(Collections.emptyList());
    }
}
