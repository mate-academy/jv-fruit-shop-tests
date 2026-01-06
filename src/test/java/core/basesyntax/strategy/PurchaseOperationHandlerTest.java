package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Inventory;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private Inventory inventory;
    private PurchaseOperationHandler handler;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        handler = new PurchaseOperationHandler();
    }

    @Test
    public void handleSubstractsQuantityCorrectly() {
        inventory.setFruitQuantity("apple", 10);
        transaction = new Transaction(Operation.PURCHASE, "apple", 5);
        handler.handle(transaction, inventory);
        assertEquals(5, inventory.getFruitQuantity("apple"));
    }

    @Test
    public void handleSubtractsToZero() {
        inventory.setFruitQuantity("apple", 5);
        transaction = new Transaction(Operation.PURCHASE, "apple", 5);
        handler.handle(transaction, inventory);
        assertEquals(0, inventory.getFruitQuantity("apple"));
    }

    @Test
    public void handleThrowsExceptionWhenQuantityBecomesNegative() {
        inventory.setFruitQuantity("apple", 5);
        transaction = new Transaction(Operation.PURCHASE, "apple", 6);
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> handler.handle(transaction, inventory)
        );
        assertEquals("Quantity can`t be less than 0!", exception.getMessage());
        assertEquals(5, inventory.getFruitQuantity("apple"));
    }
}
