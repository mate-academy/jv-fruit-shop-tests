package core.basesyntax.service.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceTransactionHandlerTest {

    private static TransactionHandler transactionHandler;
    private static final String BANANA = "banana";

    @BeforeAll
    static void beforeAll() {
        transactionHandler = new BalanceTransactionHandler(new FruitDaoImpl());
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.replace(BANANA, 10);
    }

    @Test
    void handleTransaction_nullFruitTransaction_NotOk() {
        assertThrows(NullPointerException.class,
                () -> transactionHandler.handleTransaction(null));
    }

    @Test
    void handleTransaction_balanceLowerThanZero_notOk() {
        assertThrows(RuntimeException.class,
                () -> transactionHandler.handleTransaction(new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, BANANA, -11)));
    }

    @Test
    void handleTransaction_normalValues_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(BANANA, 20);
        transactionHandler.handleTransaction(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, BANANA, 10));
        assertEquals(expected, Storage.fruits);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruits.clear();
    }
}
