package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private OperationHandler returnOperation;

    @BeforeEach
    public void setUp() {
        returnOperation = new ReturnOperation();
        Storage.setFruitQuantity("banana", 10);
    }

    @Test
    void test_apply_successfulReturn_quantityIncreased() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 5);

        returnOperation.apply(fruitTransaction);

        assertEquals(15, Storage.getFruitQuantity("banana"));
    }

    @Test
    void test_apply_negativeReturn_throwsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", -2);

        assertThrows(RuntimeException.class, () -> returnOperation.apply(fruitTransaction));
    }
}
