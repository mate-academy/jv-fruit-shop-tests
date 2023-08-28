package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private OperationHandler operationHandler;
    private List<FruitTransaction> transactions;
    private Map<FruitTransaction.Operation,
            OperationHandler> mapOfOperations;
    private Map<String, Integer> testMapOfFruitsAndQuantity;

    @BeforeEach
     void setup() {
        testMapOfFruitsAndQuantity = new HashMap<>();
        testMapOfFruitsAndQuantity.put("banana", 10);
        Storage.setFruits(testMapOfFruitsAndQuantity);
        operationStrategy = new OperationStrategyImpl();
        operationHandler = new PurchaseOperation();
        transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 10));
        mapOfOperations = new HashMap<>();
        mapOfOperations.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation());
    }

    @Test
    void getOperationAndProcess_emptyInputParameters_throwsException() {
        transactions.clear();
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.getOperationAndProcess(transactions, mapOfOperations);
        });
    }

    @Test
    void getOperationAndProcess_successful() {
        operationStrategy.getOperationAndProcess(transactions, mapOfOperations);
        assertEquals(0, Storage.getFruits().get("banana"));
    }

    @Test
    void testGetOperationAndProcess_insufficientFruits_throwsException() {
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 100));
        Map<FruitTransaction.Operation, OperationHandler> operationHashMap = new HashMap<>();
        operationHashMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperationAndProcess(transactions,operationHashMap));
    }
}
