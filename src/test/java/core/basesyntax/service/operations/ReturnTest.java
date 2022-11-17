package core.basesyntax.service.operations;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReturnTest {
    @Test
    public void checkReturnOperation_Ok() {
        storage.clear();
        Return back = new Return();
        back.makeOperation("banana", 32);
        assertEquals(32, (int)storage.get("banana"));
        back.makeOperation("banana", 64);
        assertEquals(96, (int)storage.get("banana"));
        storage.clear();
    }

}
