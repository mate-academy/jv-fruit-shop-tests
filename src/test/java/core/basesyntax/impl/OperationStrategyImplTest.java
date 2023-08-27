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
            OperationHandler> operationHashMap;
    private Map<String, Integer> map;

    @BeforeEach
     void setup() {
        map = new HashMap<>();
        map.put("banana", 10);
        Storage.setFruits(map);
        operationStrategy = new OperationStrategyImpl();
        operationHandler = new PurchaseOperation();
        transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 10));
        operationHashMap = new HashMap<>();
        operationHashMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation());
    }

    @Test
    void getOperationAndProcess_emptyInputParameters_NotOk() {
        transactions.clear();
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.getOperationAndProcess(transactions, operationHashMap);
        });
    }

    @Test
    void getOperationAndProcess_successful() {
        operationStrategy.getOperationAndProcess(transactions, operationHashMap);
        assertEquals(0, Storage.getFruits().get("banana"));
    }

    @Test
    void testGetOperationAndProcess_insufficientFruits() {

        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 100));
        Map<FruitTransaction.Operation, OperationHandler> operationHashMap = new HashMap<>();
        operationHashMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperationAndProcess(transactions,operationHashMap));
    }
}
