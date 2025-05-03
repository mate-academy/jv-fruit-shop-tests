package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationStrategies;

    @BeforeAll
    public static void setUpAll() {
        operationStrategies = new HashMap<>();
        operationStrategies.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationStrategies.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategies.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationStrategies.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategy(operationStrategies);
    }

    @Test
    public void getOperationHandler_handlerExistsForOperation_ok() {
        assertInstanceOf(BalanceOperationHandler.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE));
        assertInstanceOf(SupplyOperationHandler.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.SUPPLY));
        assertInstanceOf(PurchaseOperationHandler.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.PURCHASE));
        assertInstanceOf(ReturnOperationHandler.class,
                operationStrategy.getOperationHandler(FruitTransaction.Operation.RETURN));
    }

    @Test
    public void getOperationHandler_handlerNotExistsForOperation_notOk() {
        operationStrategies.remove(FruitTransaction.Operation.BALANCE);
        assertNull(operationStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE));
        operationStrategies.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
    }
}
