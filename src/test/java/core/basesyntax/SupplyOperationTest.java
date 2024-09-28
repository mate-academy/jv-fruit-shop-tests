package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {

    private Storage storage;
    private SupplyOperation supplyOperation;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        supplyOperation = new SupplyOperation(storage);
    }

    @Test
    public void apply_validSupplyTransaction_updatesStorage() {
        String fruit = "apple";
        int supplyQuantity = 10;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                fruit, supplyQuantity);

        supplyOperation.apply(transaction);

        assertEquals(10, storage.getQuantity(fruit));
    }

    @Test
    public void apply_zeroQuantity_throwsIllegalArgumentException() {
        String fruit = "banana";
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                fruit, 0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            supplyOperation.apply(transaction);
        });
        assertEquals("Quantity must be greater than zero.", exception.getMessage());
    }

    @Test
    public void apply_negativeQuantity_throwsIllegalArgumentException() {
        String fruit = "orange";
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                fruit, -5);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            supplyOperation.apply(transaction);
        });
        assertEquals("Quantity must be greater than zero.", exception.getMessage());
    }

    @Test
    public void apply_supplyTransaction_whenFruitAlreadyInStorage_updatesQuantity() {
        String fruit = "grape";
        storage.addFruit(fruit, 5);
        int supplyQuantity = 10;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                fruit, supplyQuantity);

        supplyOperation.apply(transaction);

        assertEquals(15, storage.getQuantity(fruit));
    }
}
