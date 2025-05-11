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
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);
        handler.apply(transaction);

        assertEquals(20, Storage.get("banana"));
    }

    @Test
    void apply_existingFruitAddsToExistingValue_Ok() {
        Storage.put("banana", 10);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana",15);
        handler.apply(transaction);

        assertEquals(25, Storage.get("banana"));
    }

    @Test
    void apply_zeroQuantity_storageUnchangedOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple",0);
        handler.apply(transaction);

        assertEquals(0, Storage.get("apple"));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction transaction =
                    new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple",-10);
        });
    }

    @AfterEach
    void clearStorage() {
        Storage.clear();
    }
}
