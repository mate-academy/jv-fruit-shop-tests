package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReturnOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String BANANA = "banana";
    private static final int RETURN_QUANTITY = 10;
    private static final int INITIAL_QUANTITY = 5;

    private ReturnOperation operation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        operation = new ReturnOperation();
        storage = new HashMap<>();
    }

    @Test
    void process_fruitAbsentInStorage_shouldAddReturnedQuantity() {
        FruitTransaction tx = validTransaction(BANANA, RETURN_QUANTITY);

        operation.process(tx, storage);

        assertEquals(RETURN_QUANTITY, storage.get(BANANA));
    }

    @Test
    void process_fruitExistsInStorage_shouldIncreaseStock() {
        storage.put(BANANA, INITIAL_QUANTITY);
        FruitTransaction tx = validTransaction(BANANA, RETURN_QUANTITY);

        operation.process(tx, storage);

        assertEquals(15, storage.get(BANANA));
    }

    @Test
    void process_nullTransaction_shouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operation.process(null, storage));

        assertEquals("Transaction and storage cannot be null", exception.getMessage());
    }

    @Test
    void process_nullStorage_shouldThrowException() {
        FruitTransaction tx = validTransaction(BANANA, RETURN_QUANTITY);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operation.process(tx, null));

        assertEquals("Transaction and storage cannot be null", exception.getMessage());
    }

    private FruitTransaction validTransaction(String fruit, int quantity) {
        FruitTransaction tx = new FruitTransaction();
        tx.setFruit(fruit);
        tx.setQuantity(quantity);
        return tx;
    }
}
