package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    public void constructorSetsAllFieldsCorrectly() {
        Operation operation = Operation.SUPPLY;
        String fruit = "apple";
        int quantity = 10;
        Transaction transaction = new Transaction(operation, fruit, quantity);
        assertEquals(operation, transaction.getOperation());
        assertEquals(fruit, transaction.getFruit());
        assertEquals(quantity, transaction.getQuantity());
    }

    @Test
    public void constructorReturnsZeroQuantity() {
        assertEquals(0, new Transaction(Operation.BALANCE, "apple", 0).getQuantity());
    }

    @Test
    public void constructorReturnsNull() {
        assertNull(new Transaction(Operation.SUPPLY, null, 5).getFruit());
    }
}
