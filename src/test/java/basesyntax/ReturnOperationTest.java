package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        FruitTransaction transaction = createTransaction(BANANA, RETURN_QUANTITY);

        operation.process(transaction, storage);

        assertEquals(RETURN_QUANTITY, storage.get(BANANA));
    }

    @Test
    void process_fruitExistsInStorage_shouldIncreaseStock() {
        storage.put(BANANA, INITIAL_QUANTITY);
        FruitTransaction transaction = createTransaction(BANANA, RETURN_QUANTITY);

        operation.process(transaction, storage);

        assertEquals(15, storage.get(BANANA));
    }

    private FruitTransaction createTransaction(String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }

    @Test
    void process_shouldIncreaseExistingFruit() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 5);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("banana");
        tx.setQuantity(3);

        operation.process(tx, storage);

        assertEquals(8, storage.get("banana"));
    }
}
