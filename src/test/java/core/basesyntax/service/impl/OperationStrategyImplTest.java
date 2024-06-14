package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.service.CantWorkWithThisFileException;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OperationStrategyImplTest {
    OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandler());
        //operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getHandler_IncorrectOperation_NotOk() {
        Operation exampleIncorrectOperation = Operation.PURCHASE;
        assertThrows(CantWorkWithThisFileException.class,
                () -> operationStrategy.getHandler(exampleIncorrectOperation),
                "Incorrect operation");
    }
}
