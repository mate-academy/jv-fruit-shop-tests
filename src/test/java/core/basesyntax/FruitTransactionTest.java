package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.fruitshop.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void createAndRetrieveTransaction() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 15);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation(),
                "Operation should match");
        assertEquals("banana", transaction.getFruit(), "Fruit should match");
        assertEquals(15, transaction.getQuantity(), "Quantity should match");
    }
}
