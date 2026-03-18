package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.ShopTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.ShopOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopOperationStrategyTest {
    private static Map<ShopTransaction.Operation, ShopOperationHandler> handlers;
    private static OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        handlers = new HashMap<>();
        operationStrategy = new ShopOperationStrategy(handlers);
    }

    @Test
    void constructor_handlersIsNull_notOk() {
        assertThrows(NullPointerException.class, () -> new ShopOperationStrategy(null));
    }

    @Test
    void resolve_validCase_returnsRightHandler_ok() {
        handlers.put(ShopTransaction.Operation.BALANCE, new ShopBalanceOperationHandler());
        handlers.put(ShopTransaction.Operation.SUPPLY, new ShopSupplyOperationHandler());
        handlers.put(ShopTransaction.Operation.PURCHASE, new ShopPurchaseOperationHandler());
        handlers.put(ShopTransaction.Operation.RETURN, new ShopReturnOperationHandler());

        assertEquals(ShopSupplyOperationHandler.class,
                operationStrategy.resolve(ShopTransaction.Operation.SUPPLY).getClass(),
                "resolve() return wrong handler");

    }

    @Test
    void resolve_operationTypeArgumentIsNull_notOk() {
        assertThrows(NullPointerException.class, () ->
                operationStrategy.resolve(null));
    }

    @Test
    void resolve_handlerInMapIsNull_notOk() {
        handlers.put(ShopTransaction.Operation.BALANCE, new ShopBalanceOperationHandler());
        handlers.put(ShopTransaction.Operation.SUPPLY, new ShopSupplyOperationHandler());
        handlers.put(ShopTransaction.Operation.PURCHASE, new ShopPurchaseOperationHandler());
        handlers.put(ShopTransaction.Operation.RETURN, null);

        assertThrows(RuntimeException.class, () ->
                operationStrategy.resolve(ShopTransaction.Operation.RETURN));

    }
}
