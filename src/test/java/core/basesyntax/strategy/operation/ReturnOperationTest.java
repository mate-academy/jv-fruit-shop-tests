package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new ReturnOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 100);
        Storage.fruits.put("orange", 200);
        Storage.fruits.put("pear", 300);
    }

    @Test
    void getOperation_ReturnFruitExists_Ok() {
        String fruit = "apple";
        int amount = 10;
        int expected = Storage.fruits.get(fruit) + amount;
        operationHandler.getOperation(fruit, amount);
        assertEquals(expected,
                Storage.fruits.get(fruit),
                "Test getOperation_ReturnFruitExists_Ok failed"
        );
    }

    @Test
    void getOperation_ReturnFruitNotExists_Ok() {
        String fruit = "mango";
        int amount = 10;
        operationHandler.getOperation(fruit, amount);
        assertEquals(amount,
                Storage.fruits.get(fruit),
                "Test getOperation_ReturnFruitNotExists_Ok failed"
        );
    }

}
