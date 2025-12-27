package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void getter_Work_Ok() {
        Operation operation = Operation.BALANCE;
        String fruit = "banana";
        int quantity = 10;

        FruitTransaction newFruitTransaction = new FruitTransaction(operation, fruit, quantity);
        assertEquals(operation, newFruitTransaction.getOperation());
        assertEquals(fruit,newFruitTransaction.getFruit());
        assertEquals(quantity,newFruitTransaction.getQuantity());
    }
}

