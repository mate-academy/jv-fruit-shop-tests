package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void constructor_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", -24),
                "IllegalArgumentException expected");
    }

    @Test
    void constructor_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 13),
                "IllegalArgumentException expected");
    }

    @Test
    void constructor_blankFruit_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.SUPPLY, " ", 13),
                "IllegalArgumentException expected");

        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.RETURN, " ", 13),
                "IllegalArgumentException expected");
    }
}
