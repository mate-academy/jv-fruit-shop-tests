package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static OperationHandler supplyHandler;

    @BeforeAll
    static void setUp() {
        supplyHandler = new SupplyOperationHandler();
    }

    @BeforeEach
    void setStorageUpBefore() {
        Storage.getStorage().put("apple", 10);
    }

    @AfterEach
    void cleanUp() {
        Storage.getStorage().clear();
    }

    @Test
    void handle_ValidData_Ok() {
        supplyHandler.handle("apple", 7);
        assertEquals(17, (int) Storage.getStorage().get("apple"));
    }

    @Test
    void handle_NegativeData_NotOk() {
        assertThrows(RuntimeException.class,
                () -> supplyHandler.handle("apple", -10));
    }

    @Test
    void handle_NullFruit_NotOk() {
        assertThrows(RuntimeException.class,
                () -> supplyHandler.handle(null, 10));
    }

    @Test
    void handle_ZeroData_Ok() {
        supplyHandler.handle("apple", 0);
        assertEquals(10, (int) Storage.getStorage().get("apple"));
    }
}
