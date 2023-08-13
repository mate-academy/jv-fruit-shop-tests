package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy strategy;
    private static List<FruitTransaction> transactions;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void setup() {
        strategy = new OperationStrategyImpl();
        transactions = new ArrayList<>();
        operationHandlerMap = new HashMap<>();
    }

    @Test
    void getOperationAndProcess_emptyListOfTransaction_NotOk() {
        List<FruitTransaction> testListOfTransaction = new ArrayList<>();
        assertThrows(RuntimeException.class, () -> {
            strategy.getOperationAndProcess(testListOfTransaction, operationHandlerMap);
        });
    }

    @Test
    void getOperationAndProcess_emptyHashMapOfHandlers_NotOk() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        assertThrows(RuntimeException.class, () -> {
            strategy.getOperationAndProcess(transactions, map);
        });
    }
}
