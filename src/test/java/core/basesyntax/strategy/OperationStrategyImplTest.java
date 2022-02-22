package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.QuantityAddOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void get_BalanceOperation_Ok() {
        OperationHandler balanceHandler = new QuantityAddOperationHandler();
        assertEquals(balanceHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    public void get_SupplyOperation_Ok() {
        OperationHandler supplyHandler = new QuantityAddOperationHandler();
        assertEquals(supplyHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass());
    }

    @Test
    public void get_ReturnOperation_Ok() {
        OperationHandler returnHandler = new QuantityAddOperationHandler();
        assertEquals(returnHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.RETURN).getClass());
    }

    @Test
    public void get_PurchaseOperation_Ok() {
        OperationHandler purchaseHandler = new PurchaseOperationHandler();
        assertEquals(purchaseHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass());
    }

    @Test(expected = RuntimeException.class)
    public void get_NullOperation_NotOk() {
        operationStrategy.get(null).getClass();
    }
}
