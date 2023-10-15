package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    void introduceFruit_Ok() {
        String fruit = "banana";
        fruitTransaction.setFruit(fruit);
        assertEquals(fruit, fruitTransaction.getFruit());
    }

    @Test
    void introduceQuantity_Ok() {
        int quantity = 12;
        fruitTransaction.setQuantity(quantity);
        assertEquals(quantity, fruitTransaction.getQuantity());
    }

    @Test
    void introduceOperation_Ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        fruitTransaction.setOperation(operation);
        assertEquals(operation, fruitTransaction.getOperation());
    }

    @Test
    void introduceOperationFromCode_Ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.fromCode("s");
        fruitTransaction.setOperation(operation);
        assertEquals(operation, FruitTransaction.Operation.SUPPLY);
    }

    @Test
    void introduceOperationFromCode_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("k"));
    }
}
