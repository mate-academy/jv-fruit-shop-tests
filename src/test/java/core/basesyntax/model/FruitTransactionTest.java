package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void constructor_validValues_ok() {
        FruitTransaction transaction =
                new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY,
                        "banana",
                        10
                );

        assertEquals(FruitTransaction.Operation.SUPPLY,
                transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void setters_validValues_ok() {
        FruitTransaction transaction = new FruitTransaction();

        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("apple");
        transaction.setQuantity(5);

        assertEquals(FruitTransaction.Operation.PURCHASE,
                transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(5, transaction.getQuantity());
    }

    @Test
    void setQuantity_negativeValue_notOk() {
        FruitTransaction transaction = new FruitTransaction();

        assertThrows(RuntimeException.class,
                () -> transaction.setQuantity(-1));
    }

    @Test
    void setQuantity_zero_ok() {
        FruitTransaction transaction = new FruitTransaction();

        transaction.setQuantity(0);

        assertEquals(0, transaction.getQuantity());
    }

    @Test
    void fromCode_validCodes_ok() {
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
    void fromCode_nullCode_notOk() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.fromCode(null));
    }

    @Test
    void fromCode_emptyCode_notOk() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.fromCode(""));
    }

    @Test
    void fromCode_unknownCode_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
    }
}
