package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.BalanceOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String APPLE = "apple";
    private static final int INITIAL_BALANCE = 50;
    private static final int NEW_BALANCE = 100;

    private BalanceOperation operation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        operation = new BalanceOperation();
        storage = new HashMap<>();
    }

    @Test
    void process_validTransaction_shouldSetNewBalance() {
        storage.put(APPLE, INITIAL_BALANCE);

        FruitTransaction transaction = createTransaction(APPLE, NEW_BALANCE);

        operation.process(transaction, storage);

        assertEquals(NEW_BALANCE, storage.get(APPLE));
    }

    @Test
    void process_newFruit_shouldAddFruitToStorage() {
        FruitTransaction transaction = createTransaction(APPLE, NEW_BALANCE);

        operation.process(transaction, storage);

        assertEquals(NEW_BALANCE, storage.get(APPLE));
    }

    private FruitTransaction createTransaction(String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }

    @Test
    void process_shouldInitializeNewFruit() {
        Map<String, Integer> storage = new HashMap<>();

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("apple");
        tx.setQuantity(10);

        operation.process(tx, storage);

        assertEquals(10, storage.get("apple"));
    }
}
