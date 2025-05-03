package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void transactionsWithValidCode_OK() {
        String validCode = "p";
        FruitTransaction.Operation operation = FruitTransaction.Operation.fromCode(validCode);
        assertEquals(FruitTransaction.Operation.PURCHASE, operation);
    }

    @Test
    void transactionWithInvalidCode_NotOk() {
        String invalidCode = "z";
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.fromCode(invalidCode)
        );
    }
}
