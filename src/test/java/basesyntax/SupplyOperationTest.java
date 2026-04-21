package basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    private SupplyOperation operation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        operation = new SupplyOperation();
        storage = new HashMap<>();
    }

    @Test
    void process_shouldIncreaseExistingValue() {
        storage.put("apple", 10);

        FruitTransaction tx = create("apple", 5);

        operation.process(tx, storage);

        assertEquals(15, storage.get("apple"));
    }

    @Test
    void process_shouldAddNewFruit() {
        FruitTransaction tx = create("banana", 7);

        operation.process(tx, storage);

        assertEquals(7, storage.get("banana"));
    }

    @Test
    void process_shouldHandleNullInitialValue() {
        FruitTransaction tx = create("kiwi", 3);

        operation.process(tx, storage);

        assertEquals(3, storage.get("kiwi"));
    }

    private FruitTransaction create(String fruit, int quantity) {
        FruitTransaction tx = new FruitTransaction();
        tx.setOperation(FruitTransaction.Operation.SUPPLY);
        tx.setFruit(fruit);
        tx.setQuantity(quantity);
        return tx;
    }
}
