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

public class BalanceImplTest {
    private static Storage storage;
    private static Fruit fruit;
    private Map<OperationType, Operation> operationHandlers;

    @BeforeEach
    void create() {
        storage = new Storage();
        operationHandlers = new HashMap<>();
        operationHandlers.put(OperationType.BALANCE, new BalanceImpl(storage));
    }

    @Test
    void balanceOperation_withValidTransaction_updatesStorage() {
        fruit = new Fruit("banana", 100);
        Operation balance = operationHandlers.get(OperationType.BALANCE);
        balance.execute(fruit);
        assertEquals(100, storage.get(fruit.getName()));
    }

    @Test
    void balanceOperation_withNegativeQuantity_throwsException() {
        fruit = new Fruit("banana", -100);
        Operation balance = operationHandlers.get(OperationType.BALANCE);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> balance.execute(fruit));
        assertEquals("Quantity cannot be negative", illegalArgumentException.getMessage());
    }

    @Test
    void balanceOperation_withNullFruit_throwsException() {
        Operation balance = operationHandlers.get(OperationType.BALANCE);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> balance.execute(null));
        assertEquals("Fruit cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void balanceOperation_withNullFruitName_throwsException() {
        fruit = new Fruit(null, 100);
        Operation balance = operationHandlers.get(OperationType.BALANCE);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> balance.execute(fruit));
        assertEquals("Fruit name cannot be null", illegalArgumentException.getMessage());
    }

}
