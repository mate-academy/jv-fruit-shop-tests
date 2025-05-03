package core.basesyntax.db;

import static org.junit.Assert.fail;

import core.basesyntax.service.impl.ErrorDataException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
    }

    @Test
    void updateFruitQuantity_QuantityLessThan0_NotOk() {

        try {
            storage.updateFruitQuantity("Any fruit", -1);
        } catch (ErrorDataException e) {
            return;
        }
        fail("If fruit quantity is less than 0, ErrorDataException should be thrown");
    }

    @AfterEach
    void tearDown() {
        storage = new Storage();
    }
}
