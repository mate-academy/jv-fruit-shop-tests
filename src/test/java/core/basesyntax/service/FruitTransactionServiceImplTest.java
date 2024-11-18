package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {

    private FruitTransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new FruitTransactionServiceImpl();
    }

    @Test
    void handle_shouldProcessBalanceOperation() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "Apple", 50);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(50, storage.get("Apple"));
    }

    @Test
    void handle_shouldProcessSupplyOperation() {
        transactionService.handle(new FruitTransaction(Operation.BALANCE, "Banana", 30));
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "Banana", 20);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(50, storage.get("Banana"));
    }

    @Test
    void handle_shouldProcessPurchaseOperation() {
        transactionService.handle(new FruitTransaction(Operation.BALANCE, "Orange", 40));
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "Orange", 25);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(15, storage.get("Orange"));
    }

    @Test
    void handle_shouldThrowErrorForInsufficientQuantityDuringPurchase() {
        transactionService.handle(new FruitTransaction(Operation.BALANCE, "Grapes", 10));
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "Grapes", 20);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(10, storage.get("Grapes"));
    }

    @Test
    void handle_shouldProcessReturnOperation() {
        transactionService.handle(new FruitTransaction(Operation.BALANCE, "Pineapple", 5));
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "Pineapple", 10);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(15, storage.get("Pineapple"));
    }

    @Test
    void handle_shouldAddNewFruitWithBalanceOperation() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "Strawberry", 100);
        transactionService.handle(transaction);
        Map<String, Integer> storage = transactionService.getFruitStorage();
        assertEquals(100, storage.get("Strawberry"));
    }

    @Test
    void handle_shouldIgnoreUnsupportedOperation() {
        FruitTransaction transaction = new FruitTransaction(null, "Apple", 50);
        assertThrows(NullPointerException.class, () -> transactionService.handle(transaction));
    }
}
