package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransaction;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddOperationHandlerTest {
    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void apply_AddNewFruit_ShouldAddFruitAndReturnQuantity() {
        Fruit apple = new Fruit("apple");
        FruitTransaction transaction = new FruitTransaction("ADD", apple, 10);
        AddOperationHandler handler = new AddOperationHandler();
        int updatedQuantity = handler.apply(transaction);
        int expectedQuantity = 10;
        int actualQuantity = Storage.fruits.get(apple);
        assertEquals(expectedQuantity, actualQuantity);
        assertEquals(expectedQuantity, updatedQuantity);
    }

    @Test
    void apply_UpdateExistingFruitQuantity_ShouldUpdateQuantityAndReturnUpdatedValue() {
        Fruit apple = new Fruit("apple");
        FruitTransaction transaction = new FruitTransaction("ADD", apple, 10);
        Storage.fruits.put(apple, 5);
        AddOperationHandler handler = new AddOperationHandler();
        int updatedQuantity = handler.apply(transaction);
        int expectedQuantity = 15;
        int actualQuantity = Storage.fruits.get(apple);
        assertEquals(expectedQuantity, actualQuantity);
        assertEquals(expectedQuantity, updatedQuantity);
    }

    @Test
    void apply_UpdateExistingFruitQuantityWithNegativeValue_ShouldUpdateQuantity() {
        Fruit apple = new Fruit("apple");
        FruitTransaction transaction = new FruitTransaction("ADD", apple, -5);
        Storage.fruits.put(apple, 10);
        AddOperationHandler handler = new AddOperationHandler();
        int updatedQuantity = handler.apply(transaction);
        int expectedQuantity = 5;
        int actualQuantity = Storage.fruits.get(apple);
        assertEquals(expectedQuantity, actualQuantity);
        assertEquals(expectedQuantity, updatedQuantity);
    }

    @Test
    void apply_AddNewFruitWithNegativeValue_ShouldNotAddFruitAndReturnZero() {
        Fruit apple = new Fruit("apple");
        FruitTransaction transaction = new FruitTransaction("ADD", apple, -5);
        AddOperationHandler handler = new AddOperationHandler();
        int updatedQuantity = handler.apply(transaction);
        int expectedQuantity = 0;
        int actualQuantity = Storage.fruits.getOrDefault(apple, 0);
        assertEquals(expectedQuantity, actualQuantity);
        assertEquals(expectedQuantity, updatedQuantity);
    }
}
