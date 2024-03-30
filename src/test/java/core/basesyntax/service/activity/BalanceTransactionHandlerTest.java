package core.basesyntax.service.activity;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceTransactionHandlerTest {

    private static TransactionHandler transactionHandler;

    @BeforeAll
    static void beforeAll() {
        transactionHandler = new BalanceTransactionHandler(new FruitDaoImpl());
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.replace("banana", 10);
    }

    @Test
    void handleTransaction_nullFruitTransaction_NotOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> transactionHandler.handleTransaction(null));
    }

    @Test
    void handleTransaction_balanceLowerThanZero_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> transactionHandler.handleTransaction(new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, "banana",-11)));
    }

    @Test
    void handleTransaction_normalValues_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 20);
        transactionHandler.handleTransaction(new FruitTransaction(
                FruitTransaction.Operation.BALANCE,"banana",10));
        Assertions.assertEquals(expected,Storage.fruits);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruits.clear();
    }
}
