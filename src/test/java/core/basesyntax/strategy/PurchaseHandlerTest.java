package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.impl.PurchaseHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE.put("banana", 100);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void execute_validQuantity_Ok() {
        operationHandler.execute("banana", 40);
        assertEquals(60, Storage.STORAGE.get("banana"));
    }

    @Test
    void execute_notValidQuantity_NotOk() {
        assertThrows(RuntimeException.class, () -> operationHandler.execute("banana", 140));
    }
}
