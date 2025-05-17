package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.operations.BalanceOperation;
import core.basesyntax.services.operations.PurchaseOperation;
import core.basesyntax.services.operations.ReturnOperation;
import core.basesyntax.services.operations.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorTest {
    private StorageService storageService;
    private TransactionProcessor processor;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImp();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation(storageService));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation(storageService));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(storageService));
        operationHandlers.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation(storageService));
        processor = new TransactionProcessor(operationHandlers);
    }

    @AfterEach
    void tearDown() {
        storageService.clear();
    }

    @Test
    void processTransactions_validTransaction_shouldUpdateStorage() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20)
        );

        processor.processTransactions(transactions);

        int expectedQuantity = 100 + 50 - 30 + 20;
        int actualQuantity = storageService.getQuantity("apple");

        assertEquals(expectedQuantity, actualQuantity);
    }
}
