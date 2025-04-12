package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static TransactionService transactionService;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
    }

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceImpl(
                new OperationStrategyImpl(operationHandlerMap)
        );
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void processData_nullData_notOk() {
        assertThrows(
                NullPointerException.class,
                () -> transactionService.procedureData(null)
        );
    }

    @Test
    void processData_validData_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(20);
        List<FruitTransaction> transactions = List.of(transaction);
        transactionService.procedureData(transactions);
        int expectedValue = 20;
        int actualValue = Storage.storage.get("banana");
        assertEquals(expectedValue, actualValue);
    }
}
