package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void constructor_workCorrectly_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 50);
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction.getOperation());
        assertEquals("banana", fruitTransaction.getFruit());
        assertEquals(50, fruitTransaction.getQuantity());
    }

    @Test
    void fromCode_returnCorrectly_Ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalidOperation_notOk() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.fromCode("x"));
    }

    @Test
    void fromCode_NullOperation_notOk() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.fromCode(null));
    }
}
