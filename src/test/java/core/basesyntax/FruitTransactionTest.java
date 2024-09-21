package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void fromCode_InvalidCode_NotOk() {
        String invalidCode = "balance";

        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.fromCode(invalidCode),
                "Invalid operation code");
    }

    @Test
    void fromCode_Ok() {
        String validCode = "b";

        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.fromCode(validCode));
    }
}
