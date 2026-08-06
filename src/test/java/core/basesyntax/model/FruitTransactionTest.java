package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void fromCode_validCode_returnOperation() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.fromCode("r");
        assertEquals(FruitTransaction.Operation.RETURN, actual);
    }

    @Test
    void fromCode_invalidCode_throwException() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("z"));
    }

}
