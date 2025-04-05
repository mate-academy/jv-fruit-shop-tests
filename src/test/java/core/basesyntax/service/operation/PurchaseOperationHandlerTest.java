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

class PurchaseOperationHandlerTest {

    private static PurchaseOperationHandler purchaseOperationHandler;
    private static Map<String, Integer> fruits;

    @BeforeAll
    public static void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        fruits = new HashMap<>();
        Storage.setStorage(fruits);
    }

    @Test
    public void handle_validTransaction_removeFruitFromStorage() {
        fruits.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 30);
        purchaseOperationHandler.handle(transaction);
        assertEquals(20, fruits.get("banana"));
    }

    @Test
    public void handle_existingFruit_notAvailable_throwRuntimeException() {
        fruits.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 10);
        assertThrows(RuntimeException.class, () -> purchaseOperationHandler.handle(transaction));
    }

    @Test
    public void handle_nullFruitName_addsFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, null, 15);
        assertThrows(RuntimeException.class, () -> purchaseOperationHandler.handle(transaction));
    }
}
