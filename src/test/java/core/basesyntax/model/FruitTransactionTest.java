package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void fruitTransaction_unvalidQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "apple", -3);
        });
    }

    @Test
    void fruitTransaction_unvalidInputCode_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.findOperation("x");
        });
    }
}
