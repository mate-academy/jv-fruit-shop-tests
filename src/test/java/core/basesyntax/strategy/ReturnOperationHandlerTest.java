package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Inventory;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private Inventory inventory;
    private ReturnOperationHandler handler;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        handler = new ReturnOperationHandler();
    }

    @Test
    public void handleAddsQuantityToExistingFruit() {
        inventory.setFruitQuantity("apple", 5);
        transaction = new Transaction(Operation.RETURN, "apple", 5);
        handler.handle(transaction, inventory);
        assertEquals(10, inventory.getFruitQuantity("apple"));
    }

    @Test
    public void handleAddsQuantityWhenFruitDoesNotExist() {
        transaction = new Transaction(Operation.RETURN, "apple", 5);
        handler.handle(transaction, inventory);
        assertEquals(5, inventory.getFruitQuantity("apple"));
    }
}
