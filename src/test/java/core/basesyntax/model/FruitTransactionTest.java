package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String VALID_CODE = "b";
    private static final String INVALID_CODE = "$";

    @Test
    void fromCode_validValue_Ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.fromCode(VALID_CODE);
        assertEquals(actual, FruitTransaction.Operation.BALANCE);
    }

    @Test
    void fromCode_invalidValue_throwException() {
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.fromCode(INVALID_CODE);
        });
    }
}
