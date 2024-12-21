package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
        Storage.fruits.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void apply_validFruitAndQuantity_ok() {
        balanceOperationHandler.apply("banana", 100);
        balanceOperationHandler.apply("apple", 50);

        assertEquals(100, Storage.fruits.get("banana"));
        assertEquals(50, Storage.fruits.get("apple"));
    }

    @Test
    void apply_updateExistingFruitBalance_ok() {
        Storage.fruits.put("banana", 200);
        balanceOperationHandler.apply("banana", 150);

        assertEquals(150, Storage.fruits.get("banana"));
    }

    @Test
    void apply_zeroQuantity_ok() {
        balanceOperationHandler.apply("orange", 0);

        assertEquals(0, Storage.fruits.get("orange"));
    }
}
