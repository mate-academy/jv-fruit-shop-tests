package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private final PurchaseOperation purchaseOperation = new PurchaseOperation();

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void process_validPurchase_ok() {
        Storage.fruits.put("banana", 100);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(50);

        purchaseOperation.process(transaction);

        assertEquals(50, Storage.fruits.get("banana"));
    }

    @Test
    void process_notEnoughFruits_notOk() {
        Storage.fruits.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(50);

        assertThrows(RuntimeException.class, () -> purchaseOperation.process(transaction));
    }

    @Test
    void process_purchaseFromEmptyStorage_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(10);

        assertThrows(RuntimeException.class,
                () -> purchaseOperation.process(transaction));
    }

    @Test
    void process_exactAmount_ok() {
        Storage.fruits.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(50);

        purchaseOperation.process(transaction);

        assertEquals(0, Storage.fruits.get("banana"));
    }
}
