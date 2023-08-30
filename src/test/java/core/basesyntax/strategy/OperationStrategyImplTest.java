package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.handler.BalanceOperationHandler;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseOperationHandler;
import core.basesyntax.service.handler.ReturnOperationHandler;
import core.basesyntax.service.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;
    private static FruitTransaction.Operation balanceOperation;
    private static FruitTransaction.Operation purchaseOperation;
    private static FruitTransaction.Operation returnOperation;
    private static FruitTransaction.Operation supplyOperation;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    }

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        balanceOperation = FruitTransaction.Operation.BALANCE;
        purchaseOperation = FruitTransaction.Operation.PURCHASE;
        returnOperation = FruitTransaction.Operation.RETURN;
        supplyOperation = FruitTransaction.Operation.SUPPLY;
    }

    @AfterAll
    static void afterAll() {
        operationHandlerMap.clear();
    }

    @Test
    void getOperation_operationIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> operationStrategy.getOperation(null));
    }

    @Test
    void getOperation_operationIsCorrect_Ok() {
        assertEquals(BalanceOperationHandler.class,
                operationStrategy.getOperation(balanceOperation).getClass());
        assertEquals(PurchaseOperationHandler.class,
                operationStrategy.getOperation(purchaseOperation).getClass());
        assertEquals(ReturnOperationHandler.class,
                operationStrategy.getOperation(returnOperation).getClass());
        assertEquals(SupplyOperationHandler.class,
                operationStrategy.getOperation(supplyOperation).getClass());
    }
}
