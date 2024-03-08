package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static OperationHandler supplyHandler;

    @BeforeAll
    public static void setUpAll() {
        supplyHandler = new SupplyOperationHandler();
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
    public void performOperation_regularCase_ok() {
        supplyHandler.performOperation("banana", 10);
        supplyHandler.performOperation("apple", 0);
        supplyHandler.performOperation("peach", 33);
        assertEquals(30, Storage.fruits.get("banana"));
        assertEquals(100, Storage.fruits.get("apple"));
        assertEquals(33, Storage.fruits.get("peach"));
    }
}
