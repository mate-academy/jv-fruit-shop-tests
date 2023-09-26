package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitDto;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationsHandlerTest {
    private static BalanceOperationsHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceOperationsHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    void checkInputBalance_negativeValue_throwsException() {
        FruitDto fruitDto = new FruitDto("banana", -10);
        assertThrows(RuntimeException.class, () -> balanceHandler.operation(fruitDto));
    }

    @Test
    void checkInputBalance_positiveOrZerValue_Ok() {
        FruitDto fruitDto1 = new FruitDto("banana", 0);
        FruitDto fruitDto2 = new FruitDto("apple", 1);
        assertDoesNotThrow(() -> balanceHandler.operation(fruitDto1));
        assertDoesNotThrow(() -> balanceHandler.operation(fruitDto2));
    }

    @Test
    void operation_itemPutCorrectInStorage_Ok() {
        String banana = "banana";
        int bananaValue = 100;
        Storage.STORAGE.put(banana, bananaValue);
        assertEquals(100, Storage.STORAGE.get("banana"));
    }
}
