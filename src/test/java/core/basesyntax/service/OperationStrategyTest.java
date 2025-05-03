package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;

    @BeforeAll
    static void beforeAll() {
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    void getHandler_handlerIsCorrect_ok() {
        assertEquals(BalanceOperationHandler.class,
                operationStrategy.getHandler(FruitTransaction.Operation.BALANCE).getClass());
        assertEquals(PurchaseOperationHandler.class,
                operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE).getClass());
        assertEquals(ReturnOperationHandler.class,
                operationStrategy.getHandler(FruitTransaction.Operation.RETURN).getClass());
        assertEquals(SupplyOperationHandler.class,
                operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY).getClass());
    }
}
