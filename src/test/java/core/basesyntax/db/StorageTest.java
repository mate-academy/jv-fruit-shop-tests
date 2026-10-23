package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StorageTest {
    private static Storage storage;

    @BeforeAll
    public static void setUp() {
        storage = new Storage();
    }

    @Test
    public void addFruitsTest() {
        storage.add("apple", 10);
        assertEquals(10, storage.getAll().get("apple"));
    }

    @Test
    public void addMoreFruitsTest() {
        storage.add("apple", 10);
        storage.add("apple", 5);
        assertEquals(15, storage.getAll().get("apple"));
    }

    @Test
    public void setFruitsTest() {
        storage.add("apple", 10);
        storage.set("apple", 3);
        assertEquals(3, storage.getAll().get("apple"));
    }

    @Test
    public void removeFruitsTest() {
        storage.add("apple", 10);
        storage.remove("apple", 4);
        assertEquals(6, storage.getAll().get("apple"));
    }

    @Test
    public void removeMoreThanAvailableTest() {
        storage.add("apple", 5);
        assertThrows(RuntimeException.class, () -> storage.remove("apple", 8));
    }

    @AfterEach
    public void clearStorage() {
        storage.getAll().clear();
    }
}
