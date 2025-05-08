package fruitshop.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private SupplyOperationHandler handler;

    @BeforeEach
    void setUp_handler() {
        handler = new SupplyOperationHandler();
    }

    @Test
    void apply_newFruitHasCorrectQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("banana");
        transaction.setQuantity(20);

        handler.apply(transaction);

        assertEquals(20, Storage.get("banana"));
    }

    @Test
    void apply_existingFruitAddsToExistingValue_Ok() {
        Storage.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("banana");
        transaction.setQuantity(15);

        handler.apply(transaction);

        assertEquals(25, Storage.get("banana"));
    }

    @Test
    void apply_nullFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit(null);
        transaction.setQuantity(10);

        assertThrows(NullPointerException.class, () -> handler.apply(transaction));
    }

    @Test
    void apply_zeroQuantity_storageUnchangedOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("apple");
        transaction.setQuantity(0);

        handler.apply(transaction);

        assertEquals(0, Storage.get("apple"));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("banana");

        assertThrows(IllegalArgumentException.class, () -> transaction.setQuantity(-10));
    }

    @AfterEach
    void clearStorage() {
        Storage.clear();
    }
}
