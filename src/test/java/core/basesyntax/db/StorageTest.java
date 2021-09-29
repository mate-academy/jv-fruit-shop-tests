package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import java.util.Map;
import java.util.Set;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class StorageTest {

    @Test
    public void validStorage() {
        Set<Map.Entry<Fruit, Integer>> entries = Storage.storage.entrySet();
        assertEquals(0, entries.size());
    }
}
