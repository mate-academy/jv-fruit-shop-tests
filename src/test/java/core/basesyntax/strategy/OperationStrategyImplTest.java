package core.basesyntax.strategy;

import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import org.junit.Assert;
import org.junit.Test;

public class OperationStrategyImplTest {
    private final OperationStrategy operationStrategy;
    private final FruitTransaction transaction;

    public OperationStrategyImplTest() {
        operationStrategy = new OperationStrategyImpl();
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
    }

    @Test
    public void getHandlerByTransaction_Ok() {
        Assert.assertEquals(BalanceOperationHandler.class,
                operationStrategy.getHandlerByTransaction(transaction).getClass());
    }

    @Test (expected = NullDataException.class)
    public void getHandlerByTransaction_null_NotOk() {
        operationStrategy.getHandlerByTransaction(null);
    }
}
