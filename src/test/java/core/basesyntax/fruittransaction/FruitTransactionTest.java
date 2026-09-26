package core.basesyntax.fruittransaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        transaction = new FruitTransaction(Operation.BALANCE, "banana", 1);
    }

    @Test
    void testToString() {
        String expected = System.lineSeparator()
                + "FruitTransaction{operation=BALANCE, fruitName='banana', quantity=1}";

        String actual = transaction.toString();
        System.out.println(expected);
        System.out.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    void testEquals() {
        final FruitTransaction copy =
                new FruitTransaction(Operation.BALANCE, "banana", 1);

        final FruitTransaction copy1 =
                new FruitTransaction(Operation.PURCHASE, "banana", 2);
        final FruitTransaction copy2 =
                new FruitTransaction(Operation.BALANCE, "orange", 2);
        final FruitTransaction copy3 =
                new FruitTransaction(Operation.BALANCE, "banana", 3);

        assertEquals(transaction, copy);

        assertNotEquals(transaction, copy1);
        assertNotEquals(transaction, copy2);
        assertNotEquals(transaction, copy3);
    }

    @Test
    void testHashCode() {
        int expected = Objects.hash(
                transaction.operation(),
                transaction.fruitName(),
                transaction.quantity());

        int actual = transaction.hashCode();

        assertEquals(expected, actual);
    }
}
