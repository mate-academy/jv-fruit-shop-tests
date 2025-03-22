package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void gettersOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void settersOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("banana");
        transaction.setQuantity(20);

        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(20, transaction.getQuantity());
    }

    @Test
    void getOperationFromValidCodeOk() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.getOperation("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.getOperation("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.getOperation("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.getOperation("r"));
    }

    @Test
    void getOperationFromInvalidCodeNotOk() {
        Exception exception = assertThrows(
                IllegalArgumentException.class, () -> FruitTransaction.getOperation("x"));
        assertEquals("x operation doesn't exist.", exception.getMessage());
    }

    @Test
    void equalsOk() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        FruitTransaction transaction3 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 5);

        assertEquals(transaction1, transaction2);
        assertNotEquals(transaction1, transaction3);
    }

    @Test
    void notEqualsOk() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 20);

        boolean actual = transaction1.equals(transaction2);
        boolean expected = false;

        assertEquals(actual, expected);
    }

    @Test
    void hashCodeOk() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);

        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }
}
