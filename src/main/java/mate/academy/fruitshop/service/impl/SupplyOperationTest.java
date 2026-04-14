package mate.academy.fruitshop.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import mate.academy.fruitshop.model.FruitTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SupplyOperationTest {

    private final SupplyOperation operation = new SupplyOperation();

    @Test
    void shouldIncreaseStock() {
        Map<String, Integer> storage = new HashMap<>();
        storage.put("apple", 10);

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("apple");
        tx.setQuantity(5);

        operation.process(tx, storage);

        assertEquals(15, storage.get("apple"));
    }
}
