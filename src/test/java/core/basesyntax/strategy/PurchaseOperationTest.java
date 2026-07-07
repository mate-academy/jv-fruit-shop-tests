package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private OperationHandler purchaseOperation;

    @BeforeEach
    public void setUp() {
        purchaseOperation = new PurchaseOperation();
    }

    @AfterEach
    public void tearDown() {
        Storage.getAllFruits().keySet().forEach(fruit -> Storage.updateQuantity(fruit, 0));
    }

    @Test
    public void handle_enoughFruits_ok() {
        Storage.updateQuantity("banana", 30);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "banana", 10);
        purchaseOperation.handle(transaction);

        assertEquals(20, Storage.getQuantity("banana"));
    }

    @Test
    public void handle_notEnoughFruits_notOk() {
        Storage.updateQuantity("banana", 5);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "banana", 10);

        assertThrows(RuntimeException.class, () -> {
            purchaseOperation.handle(transaction);
        });

    }
}


