package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseTransactionServiceTest {

    private static PurchaseTransactionService transactionService;

    @BeforeAll
    static void beforeAll() {
        transactionService = new PurchaseTransactionService();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void process_Ok() {
        Storage.fruits.put("test1", 10);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "test1", 5);
        transactionService.process(transaction);
        assertEquals(Storage.fruits.getOrDefault("test1", 0), 5);
    }

    @Test
    void process_NotEnoughQuantity_NotOk() {
        Storage.fruits.put("test2", 10);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "test2", 15);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> transactionService.process(transaction));
        assertEquals(exception.getMessage(),
                "Balance of fruit test2 - 10 is not enough to purchase 15");
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
