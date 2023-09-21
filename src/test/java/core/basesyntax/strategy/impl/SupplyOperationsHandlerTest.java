package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitDto;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationsHandlerTest {
    private static SupplyOperationsHandler supplyHandler;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyOperationsHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    void checkValue_negativeValue_throwsException() {
        FruitDto fruitDto = new FruitDto("banana", -10);
        assertThrows(RuntimeException.class, () -> supplyHandler.operation(fruitDto));
    }

    @Test
    void operation_storeValueAfterSupplyIsCorrect_Ok() {
        int bananaBalanceValue = 100;
        int bananaSupplyValue = 50;
        int expectedValue = bananaBalanceValue + bananaSupplyValue;
        Storage.STORAGE.put("banana", bananaBalanceValue);
        FruitDto fruitDto = new FruitDto("banana", bananaSupplyValue);
        supplyHandler.operation(fruitDto);
        assertEquals(expectedValue, Storage.STORAGE.get("banana"));
    }
}
