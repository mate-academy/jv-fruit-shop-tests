package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceTransactionServiceTest {

    private static BalanceTransactionService transactionService;

    @BeforeAll
    static void beforeAll() {
        transactionService = new BalanceTransactionService();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void process_Ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "test1", 10);
        transactionService.process(transaction);
        assertEquals(Storage.fruits.getOrDefault("test1", 0), 10);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
