package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void constructor_NegativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", -10);
        });
    }

    @Test
    void findOperation_UnsupportedCode_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.findOperation("x");
        });
    }
}
