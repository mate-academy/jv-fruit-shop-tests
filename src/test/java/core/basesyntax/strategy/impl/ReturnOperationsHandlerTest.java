package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitDto;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationsHandlerTest {
    private static ReturnOperationsHandler returnHandler;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new ReturnOperationsHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    void checkValue_negativeValue_throwsException() {
        FruitDto fruitDto = new FruitDto("banana", -10);
        assertThrows(RuntimeException.class, () -> returnHandler.operation(fruitDto));
    }

    @Test
    void operation_restValueAfterReturnIsCorrect_Ok() {
        int bananaBalanceValue = 100;
        int bananaReturnValue = 50;
        int expectedValue = bananaBalanceValue + bananaReturnValue;
        Storage.STORAGE.put("banana", bananaBalanceValue);
        FruitDto fruitDto = new FruitDto("banana", bananaReturnValue);
        returnHandler.operation(fruitDto);
        assertEquals(expectedValue, Storage.STORAGE.get("banana"));
    }
}
