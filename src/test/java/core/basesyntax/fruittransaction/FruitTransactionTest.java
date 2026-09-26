package core.basesyntax.fruittransaction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class FruitTransactionTest {
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        transaction = new FruitTransaction(Operation.BALANCE, "banana", 1);
    }

    @Test
    void testToString() {
        String expected = System.lineSeparator() + "FruitTransaction{" + "operation=" + Operation.BALANCE + ", fruitName='banana'" + ", quantity=1}";

        String actual = transaction.toString();

        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        FruitTransaction copy = new FruitTransaction(Operation.BALANCE, "banana", 1);

        FruitTransaction copy1 = new FruitTransaction(Operation.PURCHASE, "banana", 2);
        FruitTransaction copy2 = new FruitTransaction(Operation.BALANCE, "orange", 2);
        FruitTransaction copy3 = new FruitTransaction(Operation.BALANCE, "banana", 3);

        assertEquals(transaction, copy);

        assertNotEquals(transaction, copy1);
        assertNotEquals(transaction, copy2);
        assertNotEquals(transaction, copy3);
    }

    @Test
    void testHashCode() {
        int expected = Objects.hash(transaction.operation(), transaction.fruitName(), transaction.quantity());

        int actual = transaction.hashCode();

        assertEquals(expected, actual);
    }
}