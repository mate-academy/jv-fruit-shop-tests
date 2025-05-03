package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void wrongCode_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
    }

    @Test
    void transactionEquals_ok() {
        Object actual =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "Banana", 100);
        FruitTransaction expected =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "Banana", 100);
        assertEquals(expected, actual);
    }
}
