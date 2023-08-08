package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static OperationHandler returnHandler;

    @BeforeAll
    static void setUp() {
        returnHandler = new ReturnOperationHandler();
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
        returnHandler.handle("apple", 7);
        assertEquals(17, (int) Storage.getStorage().get("apple"));
    }

    @Test
    void handle_NegativeData_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> returnHandler.handle("apple", -10));
        String expected ="You can't return negative quantity";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void handle_NullFruit_NotOk() {
        assertThrows(RuntimeException.class,
                () -> returnHandler.handle(null, 10));
    }

    @Test
    void handle_ZeroData_Ok() {
        returnHandler.handle("apple", 0);
        assertEquals(10, (int) Storage.getStorage().get("apple"));
    }
}
