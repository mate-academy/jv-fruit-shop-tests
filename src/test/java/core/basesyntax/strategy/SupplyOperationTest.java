package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private OperationHandler supplyOperation;

    @BeforeEach
    public void setUp() {
        supplyOperation = new SupplyOperation();
        Storage.setFruitQuantity("banana", 5);
    }

    @Test
    void test_apply_successfulSupply_quantityIncreased() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 20);

        supplyOperation.apply(fruitTransaction);

        assertEquals(25, Storage.getFruitQuantity("banana"));
    }

    @Test
    void test_apply_negativeReturn_throwsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", -10);

        assertThrows(RuntimeException.class, () -> supplyOperation.apply(fruitTransaction));
    }
}
