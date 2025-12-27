package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Map;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @Test
    void updateInventory_addNewFruit_ok() {
        Storage.setInventory("apple", 0);
        Storage.updateInventory("apple", 10);

        assertEquals(10, Storage.getInventory().get("apple"));
    }

    @Test
    void updateInventory_addToExisting_ok() {
        Storage.setInventory("banana", 5);
        Storage.updateInventory("banana", 7);

        assertEquals(12, Storage.getInventory().get("banana"));
    }

    @Test
    void getInventory_returnsCopy_notSameReference() {
        Storage.setInventory("orange", 3);

        Map<String, Integer> inv1 = Storage.getInventory();
        Map<String, Integer> inv2 = Storage.getInventory();

        assertNotSame(inv1, inv2);
    }

    @Test
    void setInventory_setsValueCorrectly() {
        Storage.setInventory("kiwi", 15);

        assertEquals(15, Storage.getInventory().get("kiwi"));
    }
}
