package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private final ReturnOperation returnOperation = new ReturnOperation();

    @AfterEach
    void tearDown() {
        Storage.getInstance().getFruitStorage().clear();
    }

    @Test
    void handle_existingFruit_increasesFruitQuantity() {
        Storage.getInstance().getFruitStorage().put("banana", 20);

        returnOperation.handle("banana", 5);

        assertEquals(25, Storage.getInstance().getFruitStorage().get("banana"));
    }

    @Test
    void handle_missingFruit_addsFruitQuantity() {
        returnOperation.handle("apple", 5);

        assertEquals(5, Storage.getInstance().getFruitStorage().get("apple"));
    }
}
