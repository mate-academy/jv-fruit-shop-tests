package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    void constructorAndGetters_shouldInitializeCorrectly() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "apple", 100);
        assertEquals(Operation.BALANCE, fruitTransaction.getOperation());
        assertEquals("apple", fruitTransaction.getFruit());
        assertEquals(100, fruitTransaction.getQuantity());
    }

    @Test
    void toString_shouldReturnCorrectFormat() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "banana", 50);
        String expected = "FruitTransaction{operation=SUPPLY, fruit='banana', quantity=50}";
        assertEquals(expected, fruitTransaction.toString());
    }

    @Test
    void fromCode_b_shouldReturnBalance() {
        Operation operation = Operation.fromCode("b");
        assertEquals(Operation.BALANCE, operation);
    }

    @Test
    void fromCode_s_shouldReturnSupply() {
        Operation operation = Operation.fromCode("s");
        assertEquals(Operation.SUPPLY, operation);
    }

    @Test
    void fromCode_p_shouldReturnPurchase() {
        Operation operation = Operation.fromCode("p");
        assertEquals(Operation.PURCHASE, operation);
    }

    @Test
    void fromCode_r_shouldReturnReturn() {
        Operation operation = Operation.fromCode("r");
        assertEquals(Operation.RETURN, operation);
    }

    @Test
    void fromCode_invalidCode_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Operation.fromCode("x"));
    }

    @Test
    void setOperation_shouldUpdateOperation() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 10);
        transaction.setOperation(Operation.PURCHASE);
        assertEquals(Operation.PURCHASE, transaction.getOperation());
    }

    @Test
    void setFruit_shouldUpdateFruit() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 20);
        transaction.setFruit("orange");
        assertEquals("orange", transaction.getFruit());
    }

    @Test
    void setQuantity_shouldUpdateQuantity() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "mango", 5);
        transaction.setQuantity(50);
        assertEquals(50, transaction.getQuantity());
    }

    @Test
    void toString_withDifferentData_shouldReturnCorrectFormat() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "kiwi", 7);
        String expected = "FruitTransaction{operation=PURCHASE, fruit='kiwi', quantity=7}";
        assertEquals(expected, transaction.toString());
    }
}
