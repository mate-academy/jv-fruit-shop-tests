package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.operation.Operation;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    public void setters_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE,
                "banana", 20);

        fruitTransaction.setAmount(30);
        int expected1 = 30;
        int actual1 = fruitTransaction.getAmount();
        assertEquals(expected1, actual1);

        fruitTransaction.setOperation(Operation.PURCHASE);
        Operation expected2 = Operation.PURCHASE;
        Operation actual2 = fruitTransaction.getOperation();
        assertEquals(expected2, actual2);

        fruitTransaction.setName("apple");
        String expected3 = "apple";
        String actual3 = fruitTransaction.getName();
        assertEquals(expected3, actual3);
    }
}
