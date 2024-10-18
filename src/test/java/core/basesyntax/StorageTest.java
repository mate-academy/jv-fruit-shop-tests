package core.basesyntax;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StorageTest {

    private static final String BANANA = "banana";

    @Test
    void setFruitQuantity_validData_ok() {
        Storage storage = new Storage();
        storage.setFruitQuantity(BANANA, 100);
        Assertions.assertEquals(100, storage.getAllFruits().get(BANANA),
                "Wrong fruit quantity.");
    }

    @Test
    void addFruitQuantity_validData_ok() {
        Storage storage = new Storage();
        storage.setFruitQuantity(BANANA, 100);
        storage.addFruitQuantity(BANANA, 50);

        Assertions.assertEquals(150, storage.getAllFruits().get(BANANA),
                "Wrong updated fruit quantity.");
    }

    @Test
    void reduceFruitQuantity_validData_ok() {
        Storage storage = new Storage();
        storage.setFruitQuantity(BANANA, 100);
        storage.reduceFruitQuantity(BANANA, 30);

        Assertions.assertEquals(70, storage.getAllFruits().get(BANANA),
                "Wrong reduced fruit quantity.");
    }

    @Test
    void reduceFruitQuantity_insufficientQuantity_exceptionThrown() {
        Storage storage = new Storage();
        storage.setFruitQuantity(BANANA, 100);

        Assertions.assertThrows(RuntimeException.class, () -> {
            storage.reduceFruitQuantity(BANANA, 150);
        }, "Expected RuntimeException for insufficient quantity.");
    }
}
