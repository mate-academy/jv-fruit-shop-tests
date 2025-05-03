package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.Test;

public class OperationStrategyTest {
    private static final OperationStrategy operationStrategy = new OperationStrategy();
    private static final FruitTransaction fruitTransaction = new FruitTransaction(
            FruitTransaction.Operation.BALANCE, "apple", 100);

    @Test
    public void getHandler_validTransactions_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        assertEquals("invalid return class", operationStrategy
                .getHandler(fruitTransaction).getClass(), BalanceOperationHandler.class);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals("invalid return class", operationStrategy
                .getHandler(fruitTransaction).getClass(), PurchaseOperationHandler.class);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals("invalid return class", operationStrategy
                .getHandler(fruitTransaction).getClass(), SupplyOperationHandler.class);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        assertEquals("invalid return class", operationStrategy
                .getHandler(fruitTransaction).getClass(), ReturnOperationHandler.class);
    }

    @Test(expected = InvalidDataException.class)
    public void getHandler_nullTransaction_notOk() {
        operationStrategy.getHandler(null);
    }

    @Test(expected = InvalidDataException.class)
    public void getHandler_nullOperation_notOk() {
        fruitTransaction.setOperation(null);
        operationStrategy.getHandler(fruitTransaction);
    }
}
