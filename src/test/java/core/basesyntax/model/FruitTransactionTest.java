package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void constructor_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(Operation.PURCHASE, "banana", -1),
                "IllegalArgumentException expected");
    }

    @Test
    void constructor_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(Operation.PURCHASE, null, 1),
                "IllegalArgumentException expected");
    }

    @Test
    void constructor_blankFruit_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(Operation.PURCHASE, " ", 1),
                "IllegalArgumentException expected");

        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(Operation.PURCHASE, "", 1),
                "IllegalArgumentException expected");
    }
}
