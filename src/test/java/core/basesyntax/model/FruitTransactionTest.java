package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    public void constructor_validData_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);

        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(20, transaction.getQuantity());
    }

    @Test
    public void constructor_nullOperation_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(null, "banana", 20));
    }

    @Test
    public void constructor_blankFruit_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.BALANCE, " ", 20));
    }

    @Test
    public void constructor_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -1));
    }

    @Test
    public void getByCode_knownCode_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getByCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getByCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getByCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getByCode("r"));
    }

    @Test
    public void getByCode_unknownCode_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getByCode("unknown"));
    }
}
