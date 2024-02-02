package core.basesyntax.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void testToStringWithNullValue() {
        FruitTransaction transaction = new FruitTransaction(null, null, 0);
        assertDoesNotThrow(() -> transaction.toString());
    }
}
