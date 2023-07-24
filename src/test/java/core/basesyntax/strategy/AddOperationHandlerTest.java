package core.basesyntax.strategy;


import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.AddOperationHandler;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddOperationHandlerTest {
    private AddOperationHandler addOperationHandler;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        addOperationHandler = new AddOperationHandler();
        Field fruitsField = Storage.class.getDeclaredField("fruits");
        fruitsField.setAccessible(true);
        fruitsField.set(null, new HashMap<>()); // Initialize the static Storage.fruits map
    }

    @Test
    public void testApply_AddingFruitQuantityToStorage() {
        // Arrange
        Fruit fruit = new Fruit("Apple");
        Transaction transaction = new Transaction("add", fruit, 5);
        int previousValue = Storage.fruits.getOrDefault(fruit, 0);

        // Act
        int newValue = addOperationHandler.apply(transaction);

        // Assert
        int expectedNewValue = previousValue + 5;
        assertEquals(expectedNewValue, newValue);
    }

    @Test
    public void testApply_AddingExistingFruitToStorage() {
        // Arrange
        Fruit fruit = new Fruit("Banana");
        Storage.fruits.put(fruit, 10);
        Transaction transaction = new Transaction("add", fruit, 3);
        int previousValue = Storage.fruits.getOrDefault(fruit, 0);

        // Act
        int newValue = addOperationHandler.apply(transaction);

        // Assert
        int expectedNewValue = previousValue + 3;
        assertEquals(expectedNewValue, newValue);
    }
}