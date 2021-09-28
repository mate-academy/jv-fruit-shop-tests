package core.basesyntax.db;

import core.basesyntax.model.Fruit;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class StorageTest {

    @Test
    public void validStorage() {
        Set<Map.Entry<Fruit, Integer>> entries = Storage.storage.entrySet();
        assertEquals(0, entries.size());
    }
}