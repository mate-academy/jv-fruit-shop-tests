package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    @BeforeEach
    void setUp() {
        Storage.getFruits().clear();
    }

    @Test
    void storageShouldBeEmptyAtStart() {
        assertTrue(Storage.getFruits().isEmpty());
    }

    @Test
    void addFruitShouldBeCorrect() {
        Storage.getFruits().put("apple", 10);
        assertEquals(10, Storage.getFruits().get("apple"));
    }

    @Test
    void shouldBeReplaceStorageComplete() {
        Map<String, Integer> newData = new HashMap<>();
        newData.put("banana", 5);
        newData.put("grape", 7);

        Storage.setFruits(newData);

        assertEquals(5, Storage.getFruits().get("banana"));
        assertEquals(7, Storage.getFruits().get("grape"));
        assertEquals(2, Storage.getFruits().size());
    }

    @Test
    void shouldReflectExternalChange() {
        Map<String, Integer> externalMap = new HashMap<>();
        externalMap.put("apple", 10);

        Storage.setFruits(externalMap);

        assertEquals(10, Storage.getFruits().get("apple"));
    }

    @Test
    void setFruits_ShouldThrowException_WhenNullPassed() {
        assertThrows(IllegalArgumentException.class, () -> Storage.setFruits(null));
    }

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }
}
