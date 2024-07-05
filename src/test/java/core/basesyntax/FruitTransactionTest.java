package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void constructor_validTransaction() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 15);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(15, transaction.getQuantity());
    }

    @Test
    void constructor_nullOperation() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(null, "apple", 10));
    }

    @Test
    void constructor_emptyFruit() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "", 5));
    }

    @Test
    void constructor_negativeQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", -3));
    }

    @Test
    void constructor_zeroQuantity() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "grape", 0);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("grape", transaction.getFruit());
        assertEquals(0, transaction.getQuantity());
    }

    @Test
    void fromCode_validCode() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalidCode() {
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.fromCode("x"));
    }

    @Test
    void fromCode_nullCode() {
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.fromCode(null));
    }
}
