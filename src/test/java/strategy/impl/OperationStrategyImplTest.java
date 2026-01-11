package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.OperationHandler;
import strategy.OperationStrategy;

class OperationStrategyImplTest {
    private OperationHandler balanceHandler;
    private OperationHandler purchaseHandler;

    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperationHandler();
        purchaseHandler = new PurchaseOperationHandler();

        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);

        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    void get_validValue_Ok() {
        assertEquals(balanceHandler,
                operationStrategy.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void get_operationIsNull_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.get(null));
    }
}
