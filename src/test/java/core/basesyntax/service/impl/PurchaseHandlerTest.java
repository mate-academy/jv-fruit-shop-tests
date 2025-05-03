package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.PurchaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler = new PurchaseHandler();

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void handler_validData_ok() {
        int expected = 5;
        Storage.fruits.put("banana", 20);
        purchaseHandler.handle("banana", 15);
        assertEquals(expected, Storage.fruits.get("banana"));
    }

    @Test
    void handler_fruitNull_InvalidDataException() {
        String fruit = null;
        assertThrows(InvalidDataException.class, () ->
                purchaseHandler.handle(fruit, 5), "Fruit is not exist");
    }
}
