package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {

    private final Storage storage = new Storage();
    private final PurchaseOperation handler = new PurchaseOperation(storage);

    @AfterEach
    public void clearStorage() {
        storage.clear();
    }

    @Test
    public void apply_purchaseOperation_ok() {
        storage.addFruit("apple", 100);
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 50);
        handler.apply(transaction);
        assertEquals(50, storage.getQuantity("apple"));
    }

    @Test
    public void apply_purchaseMoreThanAvailable_throwsException() {
        storage.addFruit("apple", 50);
        FruitTransaction transaction
                = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 100);
        assertThrows(IllegalArgumentException.class, () -> handler.apply(transaction));
    }
}
