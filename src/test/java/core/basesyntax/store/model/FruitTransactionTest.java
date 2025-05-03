package core.basesyntax.store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    void fromCode_validCode_returnsCorrectOperation() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"));

        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"));

        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"));

        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalidCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
    }
}

