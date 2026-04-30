package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void process_existingFruit_shouldReplaceBalance() {
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

    @Test
    void process_nullTransaction_shouldThrowException() {
        assertThrows(RuntimeException.class, () -> operation.process(null, storage));
    }

    @Test
    void process_nullStorage_shouldThrowException() {
        FruitTransaction transaction = createTransaction(APPLE, NEW_BALANCE);

        assertThrows(RuntimeException.class,
                () -> operation.process(transaction, null));
    }

    private FruitTransaction createTransaction(String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        return transaction;
    }
}
