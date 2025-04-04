package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {

    private BalanceOperationHandler balanceOperationHandler;
    private Map<String, Integer> fruits;

    @BeforeEach
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
        fruits = new HashMap<>();
        Storage.setStorage(fruits);
    }

    @Test
    public void handle_validTransaction_addsFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 10);
        balanceOperationHandler.handle(transaction);
        assertEquals(10, fruits.get("banana"));
    }

    @Test
    public void handle_existingFruit_updatesQuantity() {
        fruits.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 10);
        balanceOperationHandler.handle(transaction);
        assertEquals(15, fruits.get("apple"));
    }

    @Test
    public void handle_nullFruitName_addsFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, null, 10);
        assertThrows(RuntimeException.class, () -> balanceOperationHandler.handle(transaction));
    }

    @Test
    public void handle_zeroQuantity_addsFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "orange", 0);
        balanceOperationHandler.handle(transaction);
        assertEquals(0, fruits.get("orange"));
    }
}
