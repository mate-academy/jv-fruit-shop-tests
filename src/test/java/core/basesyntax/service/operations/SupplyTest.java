package core.basesyntax.service.operations;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SupplyTest {
    @Test
    public void checkSupplyOperation_Ok() {
        storage.clear();
        Supply supply = new Supply();
        supply.makeOperation("banana", 128);
        assertEquals(128, (int)storage.get("banana"));
        supply.makeOperation("banana", 256);
        assertEquals(384, (int)storage.get("banana"));
        storage.clear();
    }
}
