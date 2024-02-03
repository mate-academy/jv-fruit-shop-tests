package core.basesyntax.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private final FruitTransaction transaction = new FruitTransaction(null, null, 0);

    @Test
    void testGetOperationFromNull_Ok() {
        assertDoesNotThrow(() -> transaction.getOperation());
    }

    @Test
    void testGetFruitFromNull_Ok() {
        assertDoesNotThrow(() -> transaction.getFruit());
    }

    @Test
    void testGetQuantityFromNull_Ok() {
        assertDoesNotThrow(() -> transaction.getQuantity());
    }

    @Test
    void testToStringWithNullValue() {
        //FruitTransaction transaction = new FruitTransaction(null, null, 0);
        assertDoesNotThrow(() -> transaction.toString());
    }
}
