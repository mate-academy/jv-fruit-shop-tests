package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationProcessor;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationProcessorTest {
    private static OperationProcessor operationProcessor;

    @BeforeAll
    static void beforeAll() {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(OperationType.BALANCE, new BalanceOperation());
        operationHandlerMap.put(OperationType.SUPPLY, new SupplyOperation());
        operationHandlerMap.put(OperationType.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(OperationType.RETURN, new ReturnOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        operationProcessor = new OperationProcessorImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void processConvertedData_validData_ok() {
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(OperationType.BALANCE, "banana", 20),
                new FruitTransaction(OperationType.RETURN, "banana", 100),
                new FruitTransaction(OperationType.SUPPLY, "banana", 100),
                new FruitTransaction(OperationType.PURCHASE, "banana", 20)
        );
        operationProcessor.processConvertedData(fruitTransactionList);
        assertEquals(200, Storage.STORAGE.get("banana"));
    }
}
