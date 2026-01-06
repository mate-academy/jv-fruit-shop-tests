package core.basesyntax.strategy;

import core.basesyntax.db.Inventory;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SupplyOperationHandlerTest {
    private Inventory inventory;
    private SupplyOperationHandler handler;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        handler = new SupplyOperationHandler();
    }

    @Test
    public void handleAddsQuantityToExistingFruit() {
        inventory.setFruitQuantity("apple", 5);
        transaction = new Transaction(Operation.SUPPLY, "apple", 5);
        handler.handle(transaction, inventory);
        assertEquals(10, inventory.getFruitQuantity("apple"));
    }

    @Test
    public void handleAddsQuantityWhenFruitDoesNotExist() {
        transaction = new Transaction(Operation.SUPPLY, "apple", 5);
        handler.handle(transaction, inventory);
        assertEquals(5, inventory.getFruitQuantity("apple"));
    }
}
