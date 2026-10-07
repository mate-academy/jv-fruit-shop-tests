package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put("apple", 100);
        Storage.fruits.put("orange", 200);
        Storage.fruits.put("pear", 300);
    }

    @Test
    void getOperation_SupplyFruitExists_Ok() {
        String fruit = "apple";
        int amount = 100;
        int expected = Storage.fruits.get(fruit) + amount;
        operationHandler.getOperation(fruit, amount);
        assertEquals(expected,
                Storage.fruits.get(fruit),
                "Test getOperation_SupplyFruitExists_Ok failed"
        );
    }

    @Test
    void getOperation_SupplyFruitNotExists_Ok() {
        String fruit = "mango";
        int amount = 100;
        operationHandler.getOperation(fruit, amount);
        assertEquals(amount,
                Storage.fruits.get(fruit),
                "Test getOperation_ReturnFruitNotExists_Ok failed"
        );
    }

}
