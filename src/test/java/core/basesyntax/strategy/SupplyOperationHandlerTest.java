package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private OperationHandler returnOperationHandler;

    @BeforeEach
    void setUp() {
        returnOperationHandler = new SupplyOperationHandler();
    }

    @Test
    void applyHandler_emptyStorage_Ok() {
        String fruit = "apple";
        int quantity = 100;
        returnOperationHandler.apply(fruit, quantity);
        assertEquals(quantity, Storage.getFruits().get(fruit));
    }

    @Test
    void applyHandler_existingFruit_Ok() {
        String fruit = "orange";
        int quantity = 50;
        Storage.getFruits().put(fruit, quantity);
        assertEquals(50, Storage.getFruits().get(fruit));
        returnOperationHandler.apply(fruit, quantity);
        assertEquals(100, Storage.getFruits().get(fruit));
    }

    @Test
    void applyHandler_addFruit_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 100);
        expected.put("banana", 250);
        returnOperationHandler.apply("apple", 100);
        returnOperationHandler.apply("banana", 250);
        assertEquals(expected, Storage.getFruits());
    }

    @AfterEach
    void clearStorage() {
        Storage.getFruits().clear();
    }
}
