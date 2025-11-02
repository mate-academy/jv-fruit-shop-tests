package core.basesyntax.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDaoImpl storageDaoImpl;

    @BeforeEach
    void setUp() {
        storageDaoImpl = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.clearAllFruitsQuantities();
    }

    @Test
    void setFruitQuantity_validInput_Ok() {
        storageDaoImpl.setFruitQuantity("banana", 25);
        assertEquals(25,storageDaoImpl.getFruitQuantity("banana"));
    }

    @Test
    void setFruitQuantity_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> storageDaoImpl.setFruitQuantity("banana", -1));
    }

    @Test
    void setFruitQuantity_nullFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> storageDaoImpl.setFruitQuantity(null, 20));
    }

    @Test
    void addFruitQuantity_validInput_Ok() {
        storageDaoImpl.setFruitQuantity("banana", 25);
        storageDaoImpl.addFruitQuantity("banana", 25);
        assertEquals(50,storageDaoImpl.getFruitQuantity("banana"));
    }

    @Test
    void addFruitQuantity_zeroQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> storageDaoImpl.addFruitQuantity("apple", 0));
    }

    @Test
    void addFruitQuantity_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> storageDaoImpl.addFruitQuantity("apple", -20));
    }

    @Test
    void addFruitQuantity_nullFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> storageDaoImpl.addFruitQuantity(null, 120));
    }

    @Test
    void deductFruitQuantity_validInput_Ok() {
        storageDaoImpl.setFruitQuantity("banana", 25);
        storageDaoImpl.deductFruitQuantity("banana", 15);
        assertEquals(10, storageDaoImpl.getFruitQuantity("banana"));
    }

    @Test
    void deductFruitQuantity_zeroQuantity_notOk() {
        storageDaoImpl.setFruitQuantity("banana", 25);
        assertThrows(RuntimeException.class,
                () -> storageDaoImpl.deductFruitQuantity("banana", 0));
    }

    @Test
    void deductFruitQuantity_negativeQuantity_notOk() {
        storageDaoImpl.setFruitQuantity("banana", 25);
        assertThrows(RuntimeException.class,
                () -> storageDaoImpl.deductFruitQuantity("banana", -100));
    }

    @Test
    void deductFruitQuantity_moreThanStorageQuantity_notOk() {
        storageDaoImpl.setFruitQuantity("banana", 25);
        assertThrows(RuntimeException.class,
                () -> storageDaoImpl.deductFruitQuantity("banana", 26));
    }

    @Test
    void deductFruitQuantity_nullFruit_notOk() {
        storageDaoImpl.setFruitQuantity("banana", 25);
        assertThrows(RuntimeException.class,
                () -> storageDaoImpl.deductFruitQuantity(null, 25));
    }

    @Test
    void deductFruitQuantity_equalToCurrentQuantity_Ok() {
        storageDaoImpl.setFruitQuantity("banana", 25);
        storageDaoImpl.deductFruitQuantity("banana", 25);
        assertEquals(0,storageDaoImpl.getFruitQuantity("banana"));
    }

    @Test
    void getFruitQuantity_existingFruit_Ok() {
        storageDaoImpl.setFruitQuantity("apple", 10);
        assertEquals(10, storageDaoImpl.getFruitQuantity("apple"));
    }

    @Test
    void getFruitQuantity_nullFruit_notOk() {
        storageDaoImpl.setFruitQuantity("apple", 10);
        assertThrows(RuntimeException.class,
                () -> storageDaoImpl.getFruitQuantity(null));
    }

    @Test
    void getFruitQuantity_unknownFruit_zeroReturned() {
        assertEquals(0, storageDaoImpl.getFruitQuantity("unknown"));
    }

    @Test
    void getAllFruitsQuantities_Ok() {
        storageDaoImpl.setFruitQuantity("apple", 10);
        storageDaoImpl.setFruitQuantity("orange", 25);
        storageDaoImpl.setFruitQuantity("kiwi", 7);
        Map<String, Integer> allFruitsQuantities = storageDaoImpl.getAllFruitsQuantities();
        assertEquals(10, allFruitsQuantities.get("apple"));
        assertEquals(25, allFruitsQuantities.get("orange"));
        assertEquals(7, allFruitsQuantities.get("kiwi"));
        assertEquals(3, allFruitsQuantities.size());
    }
}
