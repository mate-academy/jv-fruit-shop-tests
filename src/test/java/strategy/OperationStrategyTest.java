package strategy;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fruitshop.model.FruitTransaction;
import fruitshop.strategy.OperationHandler;
import fruitshop.strategy.OperationStrategy;
import fruitshop.strategy.impl.BalanceOperationHandler;
import fruitshop.strategy.impl.OperationStrategyImpl;
import fruitshop.strategy.impl.PurchaseOperationHandler;
import fruitshop.strategy.impl.ReturnOperationHandler;
import fruitshop.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getHandler_balanceOperation_correctHandlerReturned_ok() {
        OperationHandler handler = operationStrategy
                .getHandler(FruitTransaction.Operation.BALANCE);
        assertNotNull(handler, "Handler for BALANCE should not be null");
        assertInstanceOf(BalanceOperationHandler.class, handler,
                "Handler should be an instance of BalanceOperationHandler");
    }

    @Test
    void getHandler_supplyOperation_correctHandlerReturned_ok() {
        OperationHandler handler = operationStrategy
                .getHandler(FruitTransaction.Operation.SUPPLY);
        assertNotNull(handler, "Handler for SUPPLY should not be null");
        assertInstanceOf(SupplyOperationHandler.class, handler,
                "Handler should be an instance of SupplyOperationHandler");
    }

    @Test
    void getHandler_purchaseOperation_correctHandlerReturned_ok() {
        OperationHandler handler = operationStrategy
                .getHandler(FruitTransaction.Operation.PURCHASE);
        assertNotNull(handler, "Handler for PURCHASE should not be null");
        assertInstanceOf(PurchaseOperationHandler.class, handler,
                "Handler should be an instance of PurchaseOperationHandler");
    }

    @Test
    void getHandler_returnOperation_correctHandlerReturned_ok() {
        OperationHandler handler = operationStrategy
                .getHandler(FruitTransaction.Operation.RETURN);
        assertNotNull(handler, "Handler for RETURN should not be null");
        assertInstanceOf(ReturnOperationHandler.class, handler,
                "Handler should be an instance of ReturnOperationHandler");
    }
}
