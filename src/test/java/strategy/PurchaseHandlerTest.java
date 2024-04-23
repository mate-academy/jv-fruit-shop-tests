package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.strategy.impl.PurchaseHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private PurchaseHandlerImpl purchaseHandler;
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        purchaseHandler = new PurchaseHandlerImpl();
        transaction = new FruitTransaction();
        transaction.setFruit("apple");
        Storage.fruits.clear();
        Storage.fruits.put("apple", 20);
    }

    @Test
    void apply_successfulPurchase_decreasesQuantity() {
        transaction.setQuantity(10);
        purchaseHandler.apply(transaction);
        assertEquals(10, Storage.fruits.get("apple"));
    }

    @Test
    void apply_insufficientQuantity_throwsException() {
        transaction.setQuantity(25);
        assertThrows(RuntimeException.class, () -> purchaseHandler.apply(transaction));
    }
}
