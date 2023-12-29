package core.basesyntax.handlers;

import core.basesyntax.database.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String FRUIT_NAME = "banana";

    @AfterEach
    void tearDown() {
        FruitStorage.storageData.clear();
    }

    @Test
    void purchaseHandler_validData_Ok() {
        PurchaseHandler purchaseHandler = new PurchaseHandler();
        FruitStorage.storageData.put(FRUIT_NAME,20);
        purchaseHandler.calculateResult(FRUIT_NAME,10);
        Assertions.assertEquals(10,FruitStorage.storageData.get(FRUIT_NAME));
    }
}
