package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final BalanceHandler balanceHandler = new BalanceHandler();

    @AfterEach
    void tearDown() {
        FruitStorage.storageData.clear();
    }

    @Test
    void balanceHandler_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> balanceHandler.calculateResult(FRUIT_NAME,-2));
    }

    @Test
    void balanceHandler_validData_Ok() {
        balanceHandler.calculateResult(FRUIT_NAME,123);
        assertEquals(123, FruitStorage.storageData.get(FRUIT_NAME));
    }
}
