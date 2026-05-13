package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private final SupplyOperation supplyOperation = new SupplyOperation();

    @AfterEach
    void tearDown() {
        Storage.getInstance().getFruitStorage().clear();
    }

    @Test
    void handle_existingFruit_increasesFruitQuantity() {
        Storage.getInstance().getFruitStorage().put("banana", 20);

        supplyOperation.handle("banana", 15);

        assertEquals(35, Storage.getInstance().getFruitStorage().get("banana"));
    }

    @Test
    void handle_missingFruit_addsFruitQuantity() {
        supplyOperation.handle("apple", 15);

        assertEquals(15, Storage.getInstance().getFruitStorage().get("apple"));
    }
}
