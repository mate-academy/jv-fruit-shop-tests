package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    void fruitTransaction_gettersAndSetters_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("apple");
        transaction.setQuantity(50);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(50, transaction.getQuantity());
    }
}
