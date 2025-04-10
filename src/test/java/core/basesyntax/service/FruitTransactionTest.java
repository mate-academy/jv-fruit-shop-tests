package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void testQuantityLessThanZero_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction("r","apple",-45));
    }

    @Test
    void testInvalidOperationCode_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
    }
}
