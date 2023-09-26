package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitDto;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationsHandlerTest {
    private static PurchaseOperationsHandler purchaseHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseOperationsHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    void checkValue_negativeValue_throwsException() {
        FruitDto fruitDto = new FruitDto("banana", -10);
        assertThrows(RuntimeException.class, () -> purchaseHandler.operation(fruitDto));
    }

    @Test
    void checkBalance_purchaseValueMoreThenBalance_throwsException() {
        Storage.STORAGE.put("banana", 20);
        FruitDto fruitDto = new FruitDto("banana", 40);
        assertThrows(RuntimeException.class, () -> purchaseHandler.operation(fruitDto));
    }

    @Test
    void checkBalance_purchaseValueLessOrEqualsThenBalance_Ok() {
        Storage.STORAGE.put("banana", 20);
        Storage.STORAGE.put("apple", 20);
        FruitDto fruitDto1 = new FruitDto("banana", 20);
        FruitDto fruitDto2 = new FruitDto("apple", 10);
        assertDoesNotThrow(() -> purchaseHandler.operation(fruitDto1));
        assertDoesNotThrow(() -> purchaseHandler.operation(fruitDto2));
    }

    @Test
    void operation_restValueAfterPurchaseIsCorrect_Ok() {
        int bananaBalanceValue = 100;
        int bananaPurchaseValue = 50;
        int expectedValue = bananaBalanceValue - bananaPurchaseValue;
        Storage.STORAGE.put("banana", bananaBalanceValue);
        FruitDto fruitDto = new FruitDto("banana", bananaPurchaseValue);
        purchaseHandler.operation(fruitDto);
        assertEquals(expectedValue, Storage.STORAGE.get("banana"));
    }
}
