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

public class ReturnImplTest {
    private static Storage storage;
    private static Fruit fruit;
    private Map<OperationType, Operation> operationHandlers;

    @BeforeEach
    void create() {
        storage = new Storage();
        operationHandlers = new HashMap<>();
        operationHandlers.put(OperationType.RETURN, new ReturnImpl(storage));
    }

    @Test
    void returnOperation_withValidTransaction_updatesStorage() {
        fruit = new Fruit("apple", 50);
        Operation operationReturn = operationHandlers.get(OperationType.RETURN);
        storage.put("apple", 50);
        operationReturn.execute(fruit);
        operationReturn.execute(fruit);
        assertEquals(150, storage.get(fruit.getName()));
    }

    @Test
    void returnOperation_withNegativeQuantity_throwsException() {
        fruit = new Fruit("banana", -20);
        Operation operationReturn = operationHandlers.get(OperationType.RETURN);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> operationReturn.execute(fruit));
        assertEquals("Quantity cannot be negative", illegalArgumentException.getMessage());
    }

    @Test
    void returnOperation_withNullFruit_throwsException() {
        Operation operationReturn = operationHandlers.get(OperationType.RETURN);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> operationReturn.execute(null));
        assertEquals("Fruit cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void returnOperation_withNullFruitName_throwsException() {
        fruit = new Fruit(null, 20);
        Operation operationReturn = operationHandlers.get(OperationType.RETURN);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> operationReturn.execute(fruit));
        assertEquals("Fruit name cannot be null", illegalArgumentException.getMessage());
    }

}
