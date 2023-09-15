package core.basesyntax.service.impl.operations;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler handler = new BalanceOperationHandler();

    @AfterEach
    void afterEachTest() {
        Storage.clearStorage();
    }

    @Test
    void performOperation_nullFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> handler.performOperation(null, 10));
    }

    @Test
    void performOperation_emptyFruitName_notOk() {
        assertThrows(RuntimeException.class, () -> handler.performOperation("", 10));
    }

    @Test
    void performOperation_negativeAmount_notOk() {
        assertThrows(RuntimeException.class, () -> handler.performOperation("banana", -2));
    }

    @Test
    void performOperation_ok() {
        handler.performOperation("banana", 0);
        assertEquals(0, Storage.getFruitsBalance("banana"));
        handler.performOperation("banana", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, Storage.getFruitsBalance("banana"));
    }
}