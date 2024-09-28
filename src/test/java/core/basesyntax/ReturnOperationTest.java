package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.ReturnOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {

    private Storage storage;
    private ReturnOperation returnOperation;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        returnOperation = new ReturnOperation(storage);
    }

    @Test
    public void apply_validReturnTransaction_updatesStorage() {
        String fruit = "apple";
        storage.updateFruit(fruit, 5);
        int returnQuantity = 10;

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                fruit, returnQuantity);

        returnOperation.apply(transaction);

        assertEquals(15, storage.getQuantity(fruit));
    }

    @Test
    public void apply_zeroQuantity_throwsIllegalArgumentException() {
        String fruit = "banana";
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                fruit, 0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            returnOperation.apply(transaction);
        });
        assertEquals("Quantity must be greater than zero.", exception.getMessage());
    }

    @Test
    public void apply_negativeQuantity_throwsIllegalArgumentException() {
        String fruit = "orange";
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                fruit, -5);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            returnOperation.apply(transaction);
        });
        assertEquals("Quantity must be greater than zero.", exception.getMessage());
    }

    @Test
    public void apply_returnTransaction_whenFruitNotInStorage_updatesQuantity() {
        String fruit = "grape";
        int returnQuantity = 15;

        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                fruit, returnQuantity);

        returnOperation.apply(transaction);

        assertEquals(15, storage.getQuantity(fruit));
    }
}
