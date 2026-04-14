package mate.academy.fruitshop.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import mate.academy.fruitshop.model.FruitTransaction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReturnOperationTest {

    private final ReturnOperation operation = new ReturnOperation();

    @Test
    void shouldIncreaseStock() {
        Map<String, Integer> storage = new HashMap<>();

        FruitTransaction tx = new FruitTransaction();
        tx.setFruit("banana");
        tx.setQuantity(10);

        operation.process(tx, storage);

        assertEquals(10, storage.get("banana"));
    }
}
