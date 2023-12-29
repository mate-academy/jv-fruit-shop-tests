package core.basesyntax.handlers;

import core.basesyntax.database.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QuantityAdditionHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final QuantityAdditionHandler quantityAdditionHandler
            = new QuantityAdditionHandler();

    @AfterEach
    void tearDown() {
        FruitStorage.storageData.clear();
    }

    @Test
    void quantityAdditionHandler_validData_Ok() {
        FruitStorage.storageData.put(FRUIT_NAME,10);
        quantityAdditionHandler.calculateResult(FRUIT_NAME,10);
        Assertions.assertEquals(20,FruitStorage.storageData.get(FRUIT_NAME));
    }

    @Test
    void quantityAdditionalHandler_addToNullBalanceFruit() {
        quantityAdditionHandler.calculateResult(FRUIT_NAME,20);
        Assertions.assertEquals(20, FruitStorage.storageData.get(FRUIT_NAME));
    }
}
