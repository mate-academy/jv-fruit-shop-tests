package db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {

    @BeforeEach
    void setUp() {
        try {
            var field = Storage.class.getDeclaredField("fruitStorage");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, Integer> map = (Map<String, Integer>) field.get(null);
            map.clear();
        } catch (Exception e) {
            fail("Cannot clear fruitStorage: " + e.getMessage());
        }
    }

    @Test
    void getQuantity_noFruit_returnZero() {
        int quantity = Storage.getQuantity("pear");
        assertEquals(0, quantity);
    }

    @Test
    void setQuantity_existingFruit_updatesQuantity() {
        Storage.setQuantity("apple", 10);
        int quantity = Storage.getQuantity("apple");
        assertEquals(10, quantity);
    }

    @Test
    void addQuantity_existingFruit_increasesQuantity() {
        Storage.setQuantity("banana", 5);
        Storage.addQuantity("banana", 3);
        int quantity = Storage.getQuantity("banana");
        assertEquals(8, quantity);
    }

    @Test
    void addQuantity_newFruit_addsNewEntry() {
        Storage.addQuantity("mango", 4);
        int quantity = Storage.getQuantity("mango");
        assertEquals(4, quantity);
    }

    @Test
    void getSnapshot_existingFruits_returnsUnmodifiableMap() {
        Storage.setQuantity("orange", 7);
        Map<String, Integer> snapshot = Storage.getSnapshot();
        assertTrue(snapshot.containsKey("orange"));
        assertEquals(7, snapshot.get("orange"));
        assertThrows(UnsupportedOperationException.class, () -> snapshot.put("apple", 1));
    }
}
