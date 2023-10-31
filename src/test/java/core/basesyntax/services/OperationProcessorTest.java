package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.db.Storage;
import core.basesyntax.handlers.BalanceOperationHandler;
import core.basesyntax.handlers.PurchaseOperationHandler;
import core.basesyntax.handlers.ReturnOperationHandler;
import core.basesyntax.handlers.SupplyOperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.impl.OperationProcessorImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationProcessorTest {
    private static OperationProcessor operationProcessor;

    @BeforeAll
    static void beforeAll() {
        operationProcessor = new OperationProcessorImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler()
        ));
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void processOperation_emptyList_Ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        assertDoesNotThrow(() -> operationProcessor.manageTransactions(fruitTransactions));
    }

    @Test
    void processOperations_nonEmptyList_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10)
        );
        assertDoesNotThrow(() -> operationProcessor.manageTransactions(transactions));
    }

}
