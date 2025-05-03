package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeAll
    static void setUp() {
        balanceHandler = new BalanceOperationHandler();
    }

    @AfterEach
    void cleanUp() {
        Storage.getStorage().clear();
    }

    @Test
    void handle_ValidData_Ok() {
        balanceHandler.handle("banana", 20);
        assertTrue(Storage.getStorage().containsKey("banana"));
        assertEquals(20, (int) Storage.getStorage().get("banana"));
    }

    @Test
    void handle_NegativeData_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> balanceHandler.handle("banana", -10));
        String expected = "Balance can't be negative";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void handle_NullFruit_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> balanceHandler.handle(null, 10));
        String expected = "Fruit can't be null";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void handle_ZeroData_Ok() {
        balanceHandler.handle("apple", 0);
        assertTrue(Storage.getStorage().containsKey("apple"));
        assertEquals(0, (int) Storage.getStorage().get("apple"));
    }
}
