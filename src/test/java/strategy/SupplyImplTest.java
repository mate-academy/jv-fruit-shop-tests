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

public class SupplyImplTest {
    private static Storage storage;
    private static Fruit fruit;
    private Map<OperationType, Operation> operationHandlers;

    @BeforeEach
    void create() {
        storage = new Storage();
        operationHandlers = new HashMap<>();
        operationHandlers.put(OperationType.SUPPLY, new SupplyImpl(storage));
    }

    @Test
    void supplyOperation_withValidTransaction_updatesStorage() {
        fruit = new Fruit("banana", 40);
        Operation supply = operationHandlers.get(OperationType.SUPPLY);
        storage.put("banana", 100);
        supply.execute(fruit);
        supply.execute(fruit);
        assertEquals(180, storage.get(fruit.getName()));
    }

    @Test
    void supplyOperation_withNegativeQuantity_throwsException() {
        fruit = new Fruit("apple", -100);
        Operation supply = operationHandlers.get(OperationType.SUPPLY);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> supply.execute(fruit));
        assertEquals("Supply quantity cannot be negative", illegalArgumentException.getMessage());
    }

    @Test
    void supplyOperation_withNullFruit_throwsException() {
        Operation supply = operationHandlers.get(OperationType.SUPPLY);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> supply.execute(null));
        assertEquals("Fruit cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void supplyOperation_withNullFruitName_throwsException() {
        fruit = new Fruit(null, 30);
        Operation supply = operationHandlers.get(OperationType.SUPPLY);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> supply.execute(fruit));
        assertEquals("Fruit name cannot be null", illegalArgumentException.getMessage());
    }
}
