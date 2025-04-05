package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static SupplyOperationHandler supplyOperationHandler;
    private static Map<String, Integer> fruits;

    @BeforeAll
    public static void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
        fruits = new HashMap<>();
        Storage.setStorage(fruits);
    }

    @Test
    public void handle_validTransaction_addsFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "banana", 100);
        supplyOperationHandler.handle(transaction);
        assertEquals(100, fruits.get("banana"));
    }

    @Test
    public void handle_existingFruit_updatesQuantity() {
        fruits.put("apple", 20);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 15);
        supplyOperationHandler.handle(transaction);
        assertEquals(35, fruits.get("apple"));
    }

    @Test
    public void handle_nullFruitName_addsFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, null, 30);
        assertThrows(RuntimeException.class, () -> supplyOperationHandler.handle(transaction));
    }

    @Test
    public void handle_zeroQuantity_addsFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "orange", 0);
        supplyOperationHandler.handle(transaction);
        assertEquals(0, fruits.get("orange"));
    }
}
