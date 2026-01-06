package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Inventory;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private Inventory inventory;
    private BalanceOperationHandler handler;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        handler = new BalanceOperationHandler();
    }

    @Test
    public void handleSetsQuantityFromTransaction() {
        transaction = new Transaction(Operation.BALANCE, "apple", 10);
        handler.handle(transaction, inventory);
        assertEquals(10, inventory.getFruitQuantity("apple"));
    }

    @Test
    public void handleOverridesExistingQuantity() {
        inventory.setFruitQuantity("apple", 5);
        transaction = new Transaction(Operation.BALANCE, "apple", 20);
        handler.handle(transaction, inventory);
        assertEquals(20, inventory.getFruitQuantity("apple"));
    }
}
