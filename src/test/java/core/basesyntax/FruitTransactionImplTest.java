package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionImplTest {

    @Test
    void setters_validData_ok() {
        FruitTransaction transaction = new FruitTransaction(20, "banana",
                FruitTransaction.Operation.BALANCE);
        transaction.setQuantity(100);
        transaction.setFruit("apple");
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);

        assertEquals(100, transaction.getQuantity());
        assertEquals("apple", transaction.getFruit());
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }
}
