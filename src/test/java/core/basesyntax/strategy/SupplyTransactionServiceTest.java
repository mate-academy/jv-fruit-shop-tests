package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyTransactionServiceTest {

    private static SupplyTransactionService transactionService;

    @BeforeAll
    static void beforeAll() {
        transactionService = new SupplyTransactionService();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void process_Ok() {
        Storage.fruits.put("test1", 10);
        FruitTransaction transaction1 = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "test1", 5);
        FruitTransaction transaction2 = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "test2", 7);
        transactionService.process(transaction1);
        transactionService.process(transaction2);
        assertEquals(Storage.fruits.getOrDefault("test1", 0), 15);
        assertEquals(Storage.fruits.getOrDefault("test2", 0), 7);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
