package fruitshop.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private ReturnOperationHandler handler;

    @BeforeEach
    void setUp_handler() {
        handler = new ReturnOperationHandler();
    }

    @Test
    void apply_returnToStorage_increasedOk() {
        Storage.put("apple", 20);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("apple");
        transaction.setQuantity(10);

        handler.apply(transaction);

        assertEquals(30, Storage.get("apple"));
    }

    @Test
    void apply_returnToMissingFruit_storageInitializedOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("banana");
        transaction.setQuantity(15);

        handler.apply(transaction);

        assertEquals(15, Storage.get("banana"));
    }

    @Test
    void apply_nullFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit(null);
        transaction.setQuantity(5);

        assertThrows(NullPointerException.class, () -> handler.apply(transaction));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("orange");

        assertThrows(IllegalArgumentException.class, () -> transaction.setQuantity(-5));
    }

    @Test
    void apply_zeroQuantity_storageUnchangedOk() {
        Storage.put("kiwi", 100);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("kiwi");
        transaction.setQuantity(0);

        handler.apply(transaction);

        assertEquals(100, Storage.get("kiwi"));
    }

    @AfterEach
    void clearStorage() {
        Storage.clear();
    }
}
