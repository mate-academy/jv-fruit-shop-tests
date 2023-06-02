package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void equals_validInput_ok() {
        FruitTransaction transaction1 = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "apple", 10);
        FruitTransaction transaction2 = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "apple", 10);
        FruitTransaction transaction3 = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "banana", 20);
        assertEquals(transaction1, transaction2);
        assertNotEquals(transaction1, transaction3);
    }

    @Test
    void hashCode_validInput_ok() {
        FruitTransaction transaction1 = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "apple", 10);
        FruitTransaction transaction2 = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "apple", 10);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    void toString_validInput_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.RETURN, "orange", 5);
        assertEquals("FruitTransaction{operation=RETURN, fruit='orange', quantity=5}",
                transaction.toString());
    }

    @Test
    void getters_validInput_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "grape", 15);
        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals("grape", transaction.getFruit());
        assertEquals(15, transaction.getQuantity());
    }

    @Test
    void findOperation_validInput_ok() {
        assertEquals(FruitTransaction
                .Operation.BALANCE, FruitTransaction.Operation.findOperation("b"));
        assertEquals(FruitTransaction
                .Operation.RETURN, FruitTransaction.Operation.findOperation("r"));
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction
                        .Operation.findOperation("x"), "apple", 10));
    }

    @Test
    void operate_nullOperation_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(null, "apple", 10));
    }

    @Test
    void operate_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 10));
    }

    @Test
    void operate_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", -5));
    }
}
