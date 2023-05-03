package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.strategy.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.operation.BalanceHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorTest {
    private static TransactionProcessor processor;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
    }

    @BeforeEach
    void setUp() {
        processor =
                new TransactionProcessorImpl(new OperationHandlerStrategyImpl(operationHandlerMap));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void processData_null_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> processor.processData(null));
    }

    @Test
    void processData_validDataUsingBalanceHandler_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(20);
        List<FruitTransaction> transactions = List.of(transaction);
        processor.processData(transactions);
        int expected = 20;
        int actual = Storage.storage.get("banana");
        Assertions.assertEquals(expected, actual);
    }
}
