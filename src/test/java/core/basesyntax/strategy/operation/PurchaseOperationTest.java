package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 100);
        Storage.fruits.put("orange", 200);
        Storage.fruits.put("pear", 300);
    }

    @Test
    void getOperation_ValidPurchase_Ok() {
        String fruit = "apple";
        int amount = 10;
        int expected = Storage.fruits.get(fruit) - amount;
        operationHandler.getOperation(fruit, amount);
        assertEquals(expected,
                Storage.fruits.get(fruit),
                "Test getOperation_ValidPurchase_Ok failed"
        );
    }

    @Test
    void getOperation_BuyAllApple_Ok() {
        String fruit = "apple";
        int amount = 100;
        int expected = Storage.fruits.get(fruit) - amount;
        operationHandler.getOperation(fruit, amount);
        assertEquals(expected,
                Storage.fruits.get(fruit),
                "Test getOperation_BuyAllApple_Ok failed"
        );
    }

    @Test
    void getOperation_FruitDoesNotExist_NotOk() {
        String fruit = "mango";
        int amount = 10;
        assertThrows(RuntimeException.class,
                () -> operationHandler.getOperation(fruit, amount),
                "Test getOperation_FruitDoesNotExist_NotOk failed"
        );
    }

    @Test
    void getOperation_NotEnoughFruits_NotOk() {
        String fruit = "apple";
        int amount = 101;
        assertThrows(RuntimeException.class,
                () -> operationHandler.getOperation(fruit, amount),
                "Test getOperation_NotEnoughFruits_NotOk failed"
        );
    }
}
