package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @BeforeEach
    public void setUp() {
        Storage.clear();
    }

    @AfterEach
    public void tearDown() {
        Storage.clear();
    }

    @Test
    public void put_newFruit_ok() {
        Storage.put("banana", 20);

        assertEquals(20, Storage.getQuantity("banana"));
    }

    @Test
    public void put_existingFruit_replacesQuantity() {
        Storage.put("banana", 20);
        Storage.put("banana", 7);

        assertEquals(7, Storage.getQuantity("banana"));
    }

    @Test
    public void put_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> Storage.put("banana", -1));
    }

    @Test
    public void put_blankFruit_notOk() {
        assertThrows(IllegalArgumentException.class, () -> Storage.put(" ", 10));
    }

    @Test
    public void getQuantity_missingFruit_returnsZero() {
        assertEquals(0, Storage.getQuantity("banana"));
    }

    @Test
    public void getQuantity_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class, () -> Storage.getQuantity(null));
    }

    @Test
    public void getAll_storageChanged_returnsCopy() {
        Storage.put("banana", 20);
        Map<String, Integer> fruits = Storage.getAll();
        fruits.put("apple", 10);

        assertEquals(Map.of("banana", 20), Storage.getAll());
    }
}
