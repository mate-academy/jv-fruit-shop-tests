package fruitshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.strategy.PurchaseOperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler handler;

    @BeforeEach
    void setUp_cleanStorage() {
        handler = new PurchaseOperationHandler();
        Storage.clear();
    }

    @Test
    void apply_enoughQuantity_storageReducedOk() {
        Storage.put("apple", 50);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("apple");
        transaction.setQuantity(20);

        handler.apply(transaction);

        assertEquals(30, Storage.get("apple"));
    }

    @Test
    void apply_exactQuantity_storageZeroedOk() {
        Storage.put("banana", 10);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("banana");
        transaction.setQuantity(10);

        handler.apply(transaction);

        assertEquals(0, Storage.get("banana"));
    }

    @Test
    void apply_notEnoughQuantity_notOk() {
        Storage.put("orange", 5);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("orange");
        transaction.setQuantity(10);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> handler.apply(transaction));

        assertTrue(exception.getMessage().contains("Not enough orange"));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("apple");

        assertThrows(IllegalArgumentException.class, () -> transaction.setQuantity(-3));
    }

    @Test
    void apply_fruitNotInStorage_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("kiwi");
        transaction.setQuantity(1);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> handler.apply(transaction));

        assertTrue(exception.getMessage().contains("Not enough kiwi"));
    }

    @Test
    void apply_nullFruit_notOk() {
        Storage.put("orange", 5);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit(null);
        transaction.setQuantity(5);

        assertThrows(NullPointerException.class, () -> handler.apply(transaction));
    }
}
