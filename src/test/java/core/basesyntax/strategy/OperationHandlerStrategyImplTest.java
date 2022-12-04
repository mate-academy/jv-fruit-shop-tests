package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseHandler;
import core.basesyntax.strategy.operation.ReturnHandler;
import core.basesyntax.strategy.operation.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operations;

    @Before
    public void setUp() {
        operations = new HashMap<>();
        operations.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operations.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operations.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operations.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
    }

    @Test
    public void getBalanceOperation_ok() {
        OperationHandler actual = new OperationHandlerStrategyImpl(operations)
                .get(FruitTransaction.Operation.BALANCE);
        OperationHandler expected = new BalanceHandler();
        assertEquals(expected.getClass(),actual.getClass());
    }

    @Test
    public void getPurchaseOperation_ok() {
        OperationHandler actual = new OperationHandlerStrategyImpl(operations)
                .get(FruitTransaction.Operation.PURCHASE);
        OperationHandler expected = new PurchaseHandler();
        assertEquals(expected.getClass(),actual.getClass());
    }

    @Test
    public void getReturnOperation_ok() {
        OperationHandler actual = new OperationHandlerStrategyImpl(operations)
                .get(FruitTransaction.Operation.RETURN);
        OperationHandler expected = new ReturnHandler();
        assertEquals(expected.getClass(),actual.getClass());
    }

    @Test
    public void getSupplyOperation_ok() {
        OperationHandler actual = new OperationHandlerStrategyImpl(operations)
                .get(FruitTransaction.Operation.SUPPLY);
        OperationHandler expected = new SupplyHandler();
        assertEquals(expected.getClass(),actual.getClass());
    }
}
