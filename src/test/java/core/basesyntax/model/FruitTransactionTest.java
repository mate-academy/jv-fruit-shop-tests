package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void fromCode_validCode_Ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.fromCode("b");

        assertEquals(FruitTransaction.Operation.BALANCE, actual);
    }

    @Test
    void fromCode_invalidCode_NotOk() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x")
        );

        String expectedMessage = "Unknow operation code: x";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getCode_balance_Ok() {
        String actual = FruitTransaction.Operation.BALANCE.getCode();

        assertEquals("b", actual);
    }
}
