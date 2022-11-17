package core.basesyntax.service.operations;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BalanceTest {
    @Test
    public void checkBalanceOperation_Ok() {
        storage.clear();
        Balance balance = new Balance();
        balance.makeOperation("banana", 128);
        assertEquals(128, (int)storage.get("banana"));
        balance.makeOperation("banana", 64);
        assertEquals(64, (int)storage.get("banana"));
        storage.clear();
    }
}
