package core.basesyntax.service.impl.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private final SupplyOperationHandler handler = new SupplyOperationHandler();

    @BeforeEach
    void beforeEachTest() {
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
    void performOperation_integerOverflow_notOk() {
        handler.performOperation("banana", Integer.MAX_VALUE);
        assertThrows(RuntimeException.class, () -> handler.performOperation("banana", 1));
    }

    @Test
    void performOperation_ok() {
        handler.performOperation("banana", 0);
        assertEquals(0, Storage.getFruitsBalance("banana"));
        handler.performOperation("banana", 10);
        assertEquals(10, Storage.getFruitsBalance("banana"));
        handler.performOperation("banana", 10);
        assertEquals(20, Storage.getFruitsBalance("banana"));

        handler.performOperation("apple", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, Storage.getFruitsBalance("apple"));
    }
}
