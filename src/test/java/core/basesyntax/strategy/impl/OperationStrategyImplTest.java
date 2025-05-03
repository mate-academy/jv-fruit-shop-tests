package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void getHandlerByOperation_balanceOperation_ok() {
        Class<BalanceOperationHandler> expected = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy
                        .getHandlerByOperation(FruitTransaction.Operation.BALANCE)
                        .getClass();
        Assert.assertEquals("Operations must be the same", expected, actual);
    }

    @Test
    public void getHandlerByOperation_purchaseOperation_ok() {
        Class<PurchaseOperationHandler> expected
                = PurchaseOperationHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy
                        .getHandlerByOperation(FruitTransaction.Operation.PURCHASE)
                        .getClass();
        Assert.assertEquals("Operations must be the same", expected, actual);
    }

    @Test
    public void getHandlerByOperation_returnOp_ok() {
        Class<ReturnOperationHandler> expected = ReturnOperationHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy
                        .getHandlerByOperation(FruitTransaction.Operation.RETURN)
                        .getClass();
        Assert.assertEquals("Operations must be the same", expected, actual);
    }

    @Test
    public void getHandlerByOperation_supplyOp_ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy
                        .getHandlerByOperation(FruitTransaction.Operation.SUPPLY)
                        .getClass();
        Assert.assertEquals("Operations must be the same", expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void getHandlerByOperation_nullOperation_notOk() {
        operationStrategy.getHandlerByOperation(null).getClass();
    }
}
