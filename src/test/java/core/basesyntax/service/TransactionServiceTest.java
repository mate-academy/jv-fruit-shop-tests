package core.basesyntax.service;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceTest {
    private static OperationStrategy operationStrategy;
    private static FruitTransaction fruitTransaction;
    private static TransactionService transactionService;

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new OperationStrategyImpl(
                new OperationHandlerProvider().getOperationHandlers());
        fruitTransaction = new FruitTransaction(OperationType.BALANCE, 
                new Fruit("apple", 14));
        transactionService = new TransactionServiceImpl();
    }

    @Before
    public void setUp() {
        FruitStorage.fruits.clear();
    }

    @Test(expected = NullPointerException.class)
    public void transaction_WithNullParameters_NotOk() {
        transactionService.doOperation(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void transaction_WithNullOperationStrategy_NotOk() {
        transactionService.doOperation(null, fruitTransaction);
    }

    @Test(expected = NullPointerException.class)
    public void transaction_WithNullFruitTransaction_NotOk() {
        transactionService.doOperation(operationStrategy, null);
    }

    @Test(expected = NullPointerException.class)
    public void transaction_WithNullOperationType_NotOk() {
        transactionService.doOperation(operationStrategy, 
                new FruitTransaction(null, 
                        new Fruit("apple", 14)));
    }

    @Test(expected = RuntimeException.class)
    public void transaction_WithNullFruit_NotOk() {
        transactionService.doOperation(operationStrategy, 
                new FruitTransaction(OperationType.BALANCE,null));
    }

    @Test
    public void transaction_BasicTransaction_Ok() {
        transactionService.doOperation(operationStrategy, fruitTransaction);
        Fruit expectedFruit = new Fruit("apple", 14);
        Assert.assertEquals(expectedFruit, FruitStorage.fruits.get(0));
    }
}
