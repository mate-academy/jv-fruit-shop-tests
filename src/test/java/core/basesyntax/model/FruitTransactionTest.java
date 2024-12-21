package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void fromCode_validCode_ok() {
        String code = "s";
        FruitTransaction.Operation operation = FruitTransaction.Operation.fromCode(code);
        assertEquals(FruitTransaction.Operation.SUPPLY, operation);
    }

    @Test
    void fromCode_invalidCode_notOk() {
        String code = "x";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(code));
        assertEquals("No enum constant for code: x", exception.getMessage());
    }

    @Test
    void fromCode_nullCode_notOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(null));
        assertEquals("Code cannot be null", exception.getMessage());
    }
}
