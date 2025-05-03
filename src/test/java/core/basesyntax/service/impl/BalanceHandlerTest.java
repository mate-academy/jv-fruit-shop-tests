package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.impl.BalanceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private BalanceHandler balanceHandler = new BalanceHandler();

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void handler_fruitNull_InvalidDataException() {
        String fruit = null;
        assertThrows(InvalidDataException.class, () ->
                balanceHandler.handle(fruit, 0), "Fruit is not exist");
    }

    @Test
    void handler_validData_InvalidException() {
        String fruit = "banana";
        balanceHandler.handle(fruit, 0);
        assertTrue(Storage.fruits.containsKey(fruit));
    }
}
