package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void fromCode_InvalidCode_NotOk() {
        String wrongCode = "balance";

        assertThrows(RuntimeException.class,
                () -> FruitTransaction.fromCode(wrongCode),
                "Invalid operation code");
    }

    @Test
    void fromCode_Wrong_NotOk() {
        String wrongCode = "balance";

        assertThrows(RuntimeException.class,
                () -> FruitTransaction.fromCode(wrongCode),
                "Invalid operation code");
    }

    @Test
    void fromCode_Ok() {
        String goodCode = "b";

        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.fromCode(goodCode));
    }
}
