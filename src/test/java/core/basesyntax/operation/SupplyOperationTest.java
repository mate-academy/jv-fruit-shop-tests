package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String FRUIT = "banana";
    private static final FruitTransaction.Operation OPERATION = FruitTransaction.Operation.SUPPLY;
    private Map<String, Integer> storage;
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        supplyOperation = new SupplyOperation();
    }

    private void applySupply(int initialQuantity, int supplyQuantity) {
        storage.put(FRUIT, initialQuantity);
        FruitTransaction transaction = new FruitTransaction(OPERATION, FRUIT, supplyQuantity);
        supplyOperation.apply(storage, transaction);
    }

    @Test
    void supplyPositive_OK() {
        int initialQuantity = 100;
        int supplyQuantity = 100;
        applySupply(initialQuantity, supplyQuantity);
        assertEquals(initialQuantity + supplyQuantity, storage.get(FRUIT),
                "Failed in supplyPositive_OK: "
                        + "Expected quantity after supply should match.");
    }

    @Test
    void supplyToEmptyStorage_OK() {
        int initialQuantity = 0;
        int supplyQuantity = 100;
        applySupply(initialQuantity, supplyQuantity);
        assertEquals(supplyQuantity, storage.get(FRUIT),
                "Failed in supplyToEmptyStorage_OK: "
                        + "Expected quantity in empty storage should match supplied quantity.");
    }

    @Test
    void supplyZero_OK() {
        int initialQuantity = 0;
        int supplyQuantity = 0;
        applySupply(initialQuantity, supplyQuantity);
        assertEquals(initialQuantity, storage.get(FRUIT),
                "Failed in supplyZero_OK: "
                        + "Expected quantity should remain zero when supplying zero units.");
    }

    @Test
    void supplyNegative_NotOK() {
        int supplyQuantity = -100;
        FruitTransaction transaction = new FruitTransaction(OPERATION, FRUIT, supplyQuantity);
        assertThrows(RuntimeException.class, () -> supplyOperation.apply(storage, transaction),
                "Failed in supplyNegative_NotOK: "
                        + "Expected RuntimeException when supplying negative quantity.");
    }
}
