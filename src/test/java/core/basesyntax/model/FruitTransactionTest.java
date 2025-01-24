package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void transactionsIsOk() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.SUPPLY;
        String fruit = "banana";
        int quantity = 13;

        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
        assertEquals(operation, transaction.getOperation());
        assertEquals(fruit, transaction.getFruit());
        assertEquals(quantity, transaction.getQuantity());
    }

    @Test
    void transactionNotOk() {
        String invalidCode = "z";
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.fromCode(invalidCode)
        );
    }
}
