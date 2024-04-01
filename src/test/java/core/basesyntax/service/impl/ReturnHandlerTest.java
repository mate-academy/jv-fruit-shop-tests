package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.ReturnHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private ReturnHandler returnHandler = new ReturnHandler();

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void handler_validData_ok() {
        Storage.fruits.put("banana", 20);
        int expected = 30;
        returnHandler.handle("banana", 10);
        assertEquals(expected, Storage.fruits.get("banana"));
    }

    @Test
    void handler_fruitNull_InvalidDataException() {
        String fruit = null;
        assertThrows(InvalidDataException.class, () ->
                returnHandler.handle(fruit, 10), "Fruit is not exist");
    }
}
