package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static OperationHandler returnHandler;

    @BeforeAll
    public static void setUpAll() {
        returnHandler = new ReturnOperationHandler();
    }

    @BeforeEach
    public void setUp() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 100);
    }

    @AfterEach
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void performOperation_itemNotExists_notOk() {
        assertThrows(RuntimeException.class, () -> returnHandler.performOperation("plum", 10));
    }

    @Test
    public void performOperation_regularCase_ok() {
        returnHandler.performOperation("banana", 10);
        returnHandler.performOperation("apple", 0);
        assertEquals(30, Storage.fruits.get("banana"));
        assertEquals(100, Storage.fruits.get("apple"));
    }
}
