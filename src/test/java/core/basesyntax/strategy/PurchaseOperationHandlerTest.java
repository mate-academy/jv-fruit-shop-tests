package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransaction;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {

    private Fruit orange;
    private Fruit banana;

    @BeforeEach
    public void setUp() {
        // Reset the fruits in Storage before each test
        Storage.fruits.clear();
        // Create instances of Fruit with different names
        orange = new Fruit("Orange");
        banana = new Fruit("Banana");
    }

    @Test
    public void apply_PurchaseExistingFruit_ShouldReduceQuantityAndReturnNewValue() {
        // Arrange
        Storage.fruits.put(orange, 10);
        PurchaseOperationHandler purchaseHandler = new PurchaseOperationHandler();
        FruitTransaction transaction = new FruitTransaction("PURCHASE", orange, 5);

        // Act
        int result = purchaseHandler.apply(transaction);

        // Assert
        assertEquals(5, result);
        assertEquals(5, Storage.fruits.get(orange));
    }

    @Test
    public void apply_PurchaseNonExistingFruit_ShouldNotAddFruitAndReturnNegativeValue() {
        // Arrange
        PurchaseOperationHandler purchaseHandler = new PurchaseOperationHandler();
        FruitTransaction transaction = new FruitTransaction("PURCHASE", orange, 5);

        // Act
        int result = purchaseHandler.apply(transaction);

        // Assert
        assertEquals(-5, result);
        assertFalse(Storage.fruits.containsKey(orange));
    }
    @Test
    void apply_PurchaseMoreThanAvailableQuantity_ShouldNotGoNegativeAndReturnExistingValue() {
        Fruit apple = new Fruit("Apple");
        Storage.fruits.put(apple, 3);

        FruitTransaction transaction = new FruitTransaction("Purchase", apple, 5);
        PurchaseOperationHandler handler = new PurchaseOperationHandler();
        int result = handler.apply(transaction);

        // Assert that the result is equal to the existing quantity (3) and not negative.
        assertEquals(3, result);
    }
}
