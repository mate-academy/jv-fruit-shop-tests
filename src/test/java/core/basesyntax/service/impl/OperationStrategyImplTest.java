package core.basesyntax.service.impl;

import core.basesyntax.exceptions.OperationDefinitionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void operationGetTest_Ok() {
        Assertions.assertInstanceOf(
                BalanceOperationHandler.class,
                operationStrategy.get(FruitTransaction.Operation.BALANCE)
        );
        Assertions.assertInstanceOf(
                SupplyOperationHandler.class,
                operationStrategy.get(FruitTransaction.Operation.SUPPLY)
        );
        Assertions.assertInstanceOf(
                PurchaseOperationHandler.class,
                operationStrategy.get(FruitTransaction.Operation.PURCHASE)
        );
        Assertions.assertInstanceOf(
                ReturnOperationHandler.class,
                operationStrategy.get(FruitTransaction.Operation.RETURN)
        );
    }

    @Test
    void operationGetTest_NotOk() {
        Assertions.assertThrows(OperationDefinitionException.class, () -> {
            operationStrategy.get(null);
        });
    }
}
