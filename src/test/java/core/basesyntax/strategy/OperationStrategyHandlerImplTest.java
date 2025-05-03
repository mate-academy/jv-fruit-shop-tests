package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.OperationStrategyHandler;
import core.basesyntax.strategy.operations.BalanceHandler;
import core.basesyntax.strategy.operations.OperationStrategyHandlerImpl;
import core.basesyntax.strategy.operations.PurchaseHandler;
import core.basesyntax.strategy.operations.ReturnHandler;
import core.basesyntax.strategy.operations.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyHandlerImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategyHandler operationStrategyHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationStrategyHandler = new OperationStrategyHandlerImpl(operationHandlerMap);
    }

    @Test
    public void setOperationHandler_balance_ok() {
        OperationHandler actual = operationStrategyHandler.get(FruitTransaction.Operation.BALANCE);
        assertEquals(actual, operationHandlerMap.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    public void setOperationHandler_purchase_ok() {
        OperationHandler actual = operationStrategyHandler.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(actual, operationHandlerMap.get(FruitTransaction.Operation.PURCHASE));
    }

    @Test
    public void setOperationHandler_supply_ok() {
        OperationHandler actual = operationStrategyHandler.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(actual, operationHandlerMap.get(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    public void setOperationHandler_return_ok() {
        OperationHandler actual = operationStrategyHandler.get(FruitTransaction.Operation.RETURN);
        assertEquals(actual, operationHandlerMap.get(FruitTransaction.Operation.RETURN));
    }
}
