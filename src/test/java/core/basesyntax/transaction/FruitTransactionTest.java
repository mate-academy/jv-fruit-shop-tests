package core.basesyntax.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private final FruitTransaction transaction = new FruitTransaction(null, null, 0);

    @Test
    void getOperation_FromNull_Ok() {
        assertDoesNotThrow(() -> transaction.getOperation());
    }

    @Test
    void getFruit_FromNull_Ok() {
        assertDoesNotThrow(() -> transaction.getFruit());
    }

    @Test
    void getQuantity_FromNull_Ok() {
        assertDoesNotThrow(() -> transaction.getQuantity());
    }

    @Test
    void toString_WithNullValue_NotThrowException() {
        assertDoesNotThrow(() -> transaction.toString());
    }
}
