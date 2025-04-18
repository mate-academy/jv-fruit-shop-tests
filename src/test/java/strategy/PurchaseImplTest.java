package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import java.util.HashMap;
import java.util.Map;
import model.Fruit;
import model.FruitTransaction.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseImplTest {
    private static Storage storage;
    private static Fruit fruit;
    private Map<OperationType, Operation> operationHandlers;

    @BeforeEach
    void create() {
        storage = new Storage();
        operationHandlers = new HashMap<>();
        operationHandlers.put(OperationType.PURCHASE, new PurchaseImpl(storage));
    }

    @Test
    void purchaseOperation_withValidTransaction_updatesStorage() {
        fruit = new Fruit("banana", 20);
        Operation purchase = operationHandlers.get(OperationType.PURCHASE);
        storage.put("banana", 200);
        purchase.execute(fruit);
        purchase.execute(fruit);
        assertEquals(160, storage.get(fruit.getName()));
    }

    @Test
    void purchaseOperation_withInsufficientQuantity_throwsException() {
        fruit = new Fruit("banana", 40);
        Operation purchase = operationHandlers.get(OperationType.PURCHASE);
        storage.put("banana", 30);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> purchase.execute(fruit));
        assertEquals("Cannot purchase more fruits than available for fruit: " + fruit.getName()
                + ". Available: " + fruit.getName(), illegalArgumentException.getMessage());
    }

    @Test
    void purchaseOperation_withNegativeResultQuantity_throwsException() {
        fruit = new Fruit("banana", 20);
        Operation purchase = operationHandlers.get(OperationType.PURCHASE);
        storage.put("banana", 10);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> purchase.execute(fruit));
        assertEquals("Cannot purchase more fruits than available "
                + "for fruit: " + fruit.getName()
                + ". Available: " + fruit.getName(),
                illegalArgumentException.getMessage());
    }

    @Test
    void purchaseOperation_withZeroQuantity_throwsException() {
        fruit = new Fruit("banana", 0);
        Operation purchase = operationHandlers.get(OperationType.PURCHASE);
        storage.put("banana", 10);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> purchase.execute(fruit));
        assertEquals("You can buy zero of " + fruit.getName() + " fruits",
                illegalArgumentException.getMessage());
    }

    @Test
    void purchaseOperation_withNegativeQuantity_throwsException() {
        fruit = new Fruit("banana", -20);
        Operation purchase = operationHandlers.get(OperationType.PURCHASE);
        storage.put("banana", 50);
        assertThrows(IllegalArgumentException.class, () -> purchase.execute(fruit));
    }

    @Test
    void purchaseOperation_withNullFruit_throwsException() {
        Operation purchase = operationHandlers.get(OperationType.PURCHASE);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> purchase.execute(null));
        assertEquals("Fruit cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void purchaseOperation_withNullFruitName_throwsException() {
        fruit = new Fruit(null, 20);
        Operation purchase = operationHandlers.get(OperationType.PURCHASE);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> purchase.execute(fruit));
        assertEquals("Fruit name cannot be null", illegalArgumentException.getMessage());
    }
}
