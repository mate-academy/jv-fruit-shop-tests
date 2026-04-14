package mate.academy.fruitshop.service.impl;

import java.util.HashMap;
import java.util.Map;
import mate.academy.fruitshop.model.FruitTransaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PurchaseOperationTest {

    private final PurchaseOperation operation = new PurchaseOperation();

    @Test
    void shouldDecreaseStock_whenEnoughFruit() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 20);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("banana");
        tx.setQuantity(5);

        operation.process(tx, storage);

        assertEquals(15, storage.get("banana"));
    }

    @Test
    void shouldThrowException_whenNotEnoughStock() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("banana", 5);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("banana");
        tx.setQuantity(10);

        assertThrows(RuntimeException.class,
                () -> operation.process(tx, storage));
    }
}
