package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.ReportStrategy;
import core.basesyntax.strategy.handlers.BalanceOperation;
import core.basesyntax.strategy.handlers.PurchaseOperation;
import core.basesyntax.strategy.handlers.ReturnOperation;
import core.basesyntax.strategy.handlers.SupplyOperation;
import org.junit.Test;

public class ReportStrategyImplTest {
    private static ReportStrategy strategy = new ReportStrategyImpl();

    @Test
    public void getHandlerByOperation_regularTransactions_ok() {
        OperationHandler handler =
                strategy.getHandlerByOperation(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperation.class + " expected but was " + handler.getClass(),
                BalanceOperation.class, handler.getClass());
        handler =
                strategy.getHandlerByOperation(FruitTransaction.Operation.RETURN);
        assertEquals(BalanceOperation.class + " expected but was " + handler.getClass(),
                ReturnOperation.class, handler.getClass());
        handler =
                strategy.getHandlerByOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(BalanceOperation.class + " expected but was " + handler.getClass(),
                SupplyOperation.class, handler.getClass());
        handler =
                strategy.getHandlerByOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals(BalanceOperation.class + " expected but was " + handler.getClass(),
                PurchaseOperation.class, handler.getClass());
    }

    @Test
    public void getHandlerByOperation_nullValue_ok() {
        OperationHandler actual = strategy.getHandlerByOperation(null);
        assertEquals("Null expected but was " + actual,
                null, actual);
    }
}
