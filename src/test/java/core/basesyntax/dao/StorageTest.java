package core.basesyntax.dao;

import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StorageTest {

    @After
    public void after() {
        Storage.clear();
    }

    @Test
    public void getMap_emptyMap_ok() {
        assertEquals(Storage.getMap(), new HashMap<>());
    }

    @Test
    public void getMap_nonEmptyMap_ok() {
        Map<String, Integer> expected = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            Storage.put(1 + "hello", i);
            expected.put(1 + "hello", i);
        }
        assertEquals(Storage.getMap(), expected);
    }

    @Test
    public void put_argumentsNull_ok() {
        Map<String, Integer> expected = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            Storage.put(null, null);
            expected.put(null, null);
        }
        assertEquals(Storage.getMap(), expected);
    }

    @Test
    public void put_defaultArguments_ok() {
        Map<String, Integer> expected = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            Storage.put(1 + "default", i);
            expected.put(1 + "default", i);
        }
        assertEquals(Storage.getMap(), expected);
    }

    @Test
    public void getOrDefault_getDefaultValues_ok() {
        Map<String, Integer> expected = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            expected.put(i + "default", 0);

            assertEquals(Storage.getOrDefault(i + "default", 0), (int) expected.get(i + "default"));
        }

    }
}