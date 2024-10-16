package core.basesyntax;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void setFruitQuantity_validData_ok() {
        Storage storage = new Storage();
        storage.setFruitQuantity("banana", 100);
        Assertions.assertEquals(100, storage.getAllFruits().get("banana"),
                "Wrong fruit quantity.");
    }

    @Test
    void addFruitQuantity_validData_ok() {
        Storage storage = new Storage();
        storage.setFruitQuantity("banana", 100);
        storage.addFruitQuantity("banana", 50);

        Assertions.assertEquals(150, storage.getAllFruits().get("banana"),
                "Wrong updated fruit quantity.");
    }

    @Test
    void reduceFruitQuantity_validData_ok() {
        Storage storage = new Storage();
        storage.setFruitQuantity("banana", 100);
        storage.reduceFruitQuantity("banana", 30);

        Assertions.assertEquals(70, storage.getAllFruits().get("banana"),
                "Wrong reduced fruit quantity.");
    }

    @Test
    void reduceFruitQuantity_insufficientQuantity_exceptionThrown() {
        Storage storage = new Storage();
        storage.setFruitQuantity("banana", 100);

        Assertions.assertThrows(RuntimeException.class, () -> {
            storage.reduceFruitQuantity("banana", 150);
        }, "Expected RuntimeException for insufficient quantity.");
    }
}
