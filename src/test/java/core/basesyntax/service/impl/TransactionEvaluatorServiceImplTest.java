package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionEvaluatorService;
import core.basesyntax.strategy.CalculationStrategy;
import core.basesyntax.strategy.impl.CalculationStrategyImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionEvaluatorServiceImplTest {
    private static TransactionEvaluatorService transactionEvaluator;

    @BeforeClass
    public static void beforeClass() {
        CalculationStrategy strategy = new CalculationStrategyImpl();
        transactionEvaluator = new TransactionEvaluatorServiceImpl(strategy);
    }

    @Before
    public void beforeTest() {
        Storage.storage.clear();
    }

    @Test
    public void evaluateTransactions_Ok() {
        transactionEvaluator.process(List.of(new FruitTransaction(BALANCE, "apple", 10)));
        Assert.assertEquals(10, Storage.storage.get("apple").intValue());
        transactionEvaluator.process(List.of(
                new FruitTransaction(PURCHASE, "apple", 5),
                new FruitTransaction(RETURN, "apple", 2)));
        Assert.assertEquals(7, Storage.storage.get("apple").intValue());
    }

    @Test(expected = RuntimeException.class)
    public void evaluateTransactions_not_Ok() {
        transactionEvaluator.process(List.of(new FruitTransaction(BALANCE, "apple", 10)));
        Assert.assertEquals(10, Storage.storage.get("apple").intValue());
        transactionEvaluator.process(List.of(new FruitTransaction(PURCHASE, "apple", 20)));
    }
}
