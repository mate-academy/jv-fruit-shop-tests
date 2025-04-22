package core.basesyntax.strategyimpl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.database.FruitStock;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private FruitStock fruitStock;
    private OperationStrategyImpl operationStrategy;
    private BalanceHandler balanceHandler;
    private ReturnHandler returnHandler;
    private SupplyHandler supplyHandler;
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        fruitStock = new FruitStock();
        balanceHandler = new BalanceHandler(fruitStock);
        returnHandler = new ReturnHandler(fruitStock);
        supplyHandler = new SupplyHandler(fruitStock);
        purchaseHandler = new PurchaseHandler(fruitStock);

        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, balanceHandler);
        handlers.put(Operation.RETURN, returnHandler);
        handlers.put(Operation.SUPPLY, supplyHandler);
        handlers.put(Operation.PURCHASE, purchaseHandler);

        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void testBalanceHandler() {
        OperationHandler handler = operationStrategy.getHandler(Operation.BALANCE);
        assertTrue(handler instanceof BalanceHandler);
    }

    @Test
    void testReturnHandler() {
        OperationHandler handler = operationStrategy.getHandler(Operation.RETURN);
        assertTrue(handler instanceof ReturnHandler);
    }

    @Test
    void testSupplyHandler() {
        OperationHandler handler = operationStrategy.getHandler(Operation.SUPPLY);
        assertTrue(handler instanceof SupplyHandler);
    }

    @Test
    void testPurchaseHandler() {
        OperationHandler handler = operationStrategy.getHandler(Operation.PURCHASE);
        assertTrue(handler instanceof PurchaseHandler);
    }
}
