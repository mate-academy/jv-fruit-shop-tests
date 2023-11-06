package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handlers.BalanceOperationHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseOperationHandler;
import core.basesyntax.handlers.ReturnOperationHandler;
import core.basesyntax.handlers.SupplyOperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataFileProcessing;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataFileProcessingImplTest {
    private static DataFileProcessing dataFileProcessing;
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 100;

    @BeforeAll
    static void beforeAll() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY,
                new SupplyOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategy(operationHandlerMap);
        dataFileProcessing = new DataFileProcessingImpl(operationStrategy);
    }

    @Test
    void processData_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(APPLE);
        fruitTransaction.setOperation(Operation.BALANCE);
        fruitTransaction.setQuantity(APPLE_QUANTITY);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        dataFileProcessing.processData(fruitTransactionList);
        assertEquals(Storage.fruits.get(APPLE),APPLE_QUANTITY);
    }

    @AfterAll
    static void tearDown() {
        Storage.fruits.clear();
    }
}
