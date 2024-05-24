package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void constructorAndGetters_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        String fruitName = "apple";
        int quantity = 10;

        FruitTransaction transaction = new FruitTransaction(operation, fruitName, quantity);

        assertEquals(operation, transaction.getOperation());
        assertEquals(fruitName, transaction.getFruitName());
        assertEquals(quantity, transaction.getQuantity());
    }

    @Test
    void equalsAndHashCode_ok() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        FruitTransaction transaction3 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 5);

        assertEquals(transaction1, transaction2);
        assertNotEquals(transaction1, transaction3);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
        assertNotEquals(transaction1.hashCode(), transaction3.hashCode());
    }

    @Test
    void operation_getOperationByValue_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getOperationByValue("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getOperationByValue("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getOperationByValue("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getOperationByValue("r"));
    }

    @Test
    void operation_getOperationByValueInvalid_notOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getOperationByValue("x"));
        assertEquals("Invalid value of Operation x", exception.getMessage());
    }

    @Test
    void operation_getOperation_ok() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getOperation());
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getOperation());
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getOperation());
        assertEquals("r", FruitTransaction.Operation.RETURN.getOperation());
    }
}
