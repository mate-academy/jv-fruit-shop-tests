package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String FRUIT = "banana";
    private static final FruitTransaction.Operation OPERATION = FruitTransaction.Operation.RETURN;
    private Map<String, Integer> storage;
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        returnOperation = new ReturnOperation();
    }

    private void applyReturn(int initialQuantity, int returnQuantity) {
        storage.put(FRUIT, initialQuantity);
        FruitTransaction transaction = new FruitTransaction(OPERATION, FRUIT, returnQuantity);
        returnOperation.apply(storage, transaction);
    }

    @Test
    void returnPositive_OK() {
        int initialQuantity = 100;
        int returnQuantity = 15;
        applyReturn(initialQuantity, returnQuantity);
        assertEquals(initialQuantity + returnQuantity, storage.get(FRUIT),
                "Failed in returnPositive_OK: "
                        + "Expected quantity after positive return does not match.");
    }

    @Test
    void returnZero_OK() {
        int initialQuantity = 100;
        int returnQuantity = 0;
        applyReturn(initialQuantity, returnQuantity);
        assertEquals(initialQuantity, storage.get(FRUIT),
                "Failed in returnZero_OK: "
                        + "Expected quantity should remain unchanged when return quantity is 0.");
    }

    @Test
    void returnNegative_NotOK() {
        int returnQuantity = -15;
        FruitTransaction transaction = new FruitTransaction(OPERATION, FRUIT, returnQuantity);
        assertThrows(RuntimeException.class, () -> returnOperation.apply(storage, transaction),
                "Failed in returnNegative_NotOK: "
                        + "Expected RuntimeException when return quantity is negative.");
    }
}
