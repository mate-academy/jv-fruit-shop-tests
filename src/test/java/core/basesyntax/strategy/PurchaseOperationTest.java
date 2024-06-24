package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static PurchaseOperation purchaseOperation;
    private Map<String, Integer> inventory;

    @BeforeAll
    public static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
    }

    @BeforeEach
    public void setUp() {
        inventory = new HashMap<>();
        inventory.put("apple", 100);
    }

    @Test
    public void handle_purchaseOperation_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50);
        purchaseOperation.handle(transaction, inventory);
        assertEquals(50, inventory.get("apple"));
    }

    @Test
    public void handle_purchaseOperationInsufficientQuantity_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 150);
        assertThrows(RuntimeException.class, () ->
                purchaseOperation.handle(transaction, inventory));
    }

    @Test
    public void handle_purchaseOperationFruitNotAvailable_throwsException() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 50);
        assertThrows(RuntimeException.class, () ->
                purchaseOperation.handle(transaction, inventory));
    }
}
