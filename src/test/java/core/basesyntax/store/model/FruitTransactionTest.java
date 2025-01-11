package core.basesyntax.store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    void fromCode_validCode_returnsCorrectOperation() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"),
                "Expected BALANCE operation for code 'b'");

        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"),
                "Expected SUPPLY operation for code 's'");

        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"),
                "Expected PURCHASE operation for code 'p'");

        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"),
                "Expected RETURN operation for code 'r' (check your enum setup)");
    }

    @Test
    void fromCode_invalidCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"),
                "Expected IllegalArgumentException for invalid code 'x'");
    }
}

