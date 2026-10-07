package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 100);
        Storage.fruits.put("orange", 200);
        Storage.fruits.put("pear", 300);
    }

    @Test
    void getOperation_AddNewFruit_Ok() {
        String fruit = "banana";
        int amount = 100;
        operationHandler.getOperation(fruit, amount);
        assertEquals(amount,
                Storage.fruits.get(fruit),
                "Fruit not found, test getOperation_AddNewFruit_Ok failed"
        );
    }

    @Test
    void getOperation_UpdateAmount_Ok() {
        String fruit = "apple";
        int amount = 200;
        operationHandler.getOperation(fruit, amount);
        assertEquals(amount,
                Storage.fruits.get(fruit),
                "Fruit not found, test getOperation_UpdateAmount_Ok failed"
        );
    }
}
