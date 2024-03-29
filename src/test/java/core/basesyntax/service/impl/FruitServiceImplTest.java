package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitServiceImplTest {
    private Storage storage;
    private FruitService fruitService;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        fruitService = new FruitServiceImpl(storage);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void save_validData_isOk() {
        String nameFruit = "banana";
        int quantity = 25;
        fruitService.save(nameFruit,quantity);
        int actual = storage.getFruitQuantity("banana");
        int expected = 25;
        assertEquals(expected, actual);

    }

    @Test
    void save_addFruitWithZeroQuantity_isOk() {
        String nameFruit = "orange";
        int zeroQuantity = 0;
        fruitService.save(nameFruit, zeroQuantity);
        int actual = storage.getFruitQuantity(nameFruit);
        assertEquals(zeroQuantity, actual);
    }

    @Test
    void save_addMultipleFruits_isOk() {
        fruitService.save("apple", 5);
        fruitService.save("banana", 10);
        fruitService.save("cherry", 15);

        assertEquals(5, storage.getFruitQuantity("apple"));
        assertEquals(10, storage.getFruitQuantity("banana"));
        assertEquals(15, storage.getFruitQuantity("cherry"));
    }

    @Test
    void save_addTheSameFruitTwoTimes_isOk() {
        fruitService.save("apple", 5);
        fruitService.save("apple", 5);
        fruitService.save("cherry", 15);

        assertEquals(5, storage.getFruitQuantity("apple"));
    }

    @Test
    void save_addFruitWithNegotiveQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> fruitService.save("apple", -5));
    }

}
