package core.basesyntax.db;

import static org.junit.Assert.fail;

import core.basesyntax.service.impl.ErrorDataException;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void updateFruitQuantity_QuantityLessThan0_NotOk() {
        Storage storage = new Storage();
        try {
            storage.updateFruitQuantity("Any fruit", -1);
        } catch (ErrorDataException e) {
            return;
        }
        fail("If fruit quantity is less than 0, ErrorDataException should be thrown");
    }
}
