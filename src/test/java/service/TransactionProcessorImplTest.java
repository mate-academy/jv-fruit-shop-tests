package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.model.Operation;
import core.basesyntax.basesyntax.service.TransactionProcessor;
import core.basesyntax.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.OperationStrategy;
import core.basesyntax.basesyntax.strategy.impl.PurchaseHandlerImpl;
import core.basesyntax.basesyntax.strategy.impl.ReturnHandlerImpl;
import core.basesyntax.basesyntax.strategy.impl.SupplyHandlerImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static Map<Operation, OperationHandler> operationMap;
    private static TransactionProcessor transactionService;

    @BeforeAll
    static void beforeAll() {
        operationMap = new HashMap<>();
        operationMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        operationMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        operationMap.put(Operation.RETURN, new ReturnHandlerImpl());
    }

    @BeforeEach
    void setUp() {
        transactionService = new TransactionProcessorImpl(new OperationStrategy(operationMap));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void processPurchaseOperation_withSufficientStock_decreasesQuantity() {
        Storage.fruits.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.PURCHASE);
        transaction.setFruit("banana");
        transaction.setQuantity(20);
        List<FruitTransaction> transactions = List.of(transaction);

        transactionService.process(transactions);
        int expected = 30;
        int actual = Storage.fruits.get("banana");

        assertEquals(expected, actual, "Storage should reflect the purchase operation.");
    }

    @Test
    void processPurchaseOperation_withInsufficientStock_throwsException() {
        Storage.fruits.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.PURCHASE);
        transaction.setFruit("banana");
        transaction.setQuantity(20);

        assertThrows(RuntimeException.class, () -> transactionService.process(List.of(transaction)),
                "Should throw exception due to insufficient stock.");
    }

    @Test
    void processReturnOperation_increasesQuantity() {
        Storage.fruits.put("apple", 20);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.RETURN);
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        List<FruitTransaction> transactions = List.of(transaction);

        transactionService.process(transactions);
        int expected = 30;
        int actual = Storage.fruits.get("apple");

        assertEquals(expected, actual, "Storage should reflect the return operation.");
    }
}
