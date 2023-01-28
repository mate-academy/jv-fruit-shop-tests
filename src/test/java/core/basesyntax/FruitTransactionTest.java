package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    private FruitTransaction transaction;

    @Before
    public void setUp() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 10);
    }

    @Test
    public void fruitTransaction_operation_getOperationByValue_b_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("b");
        assertEquals(expected, actual);
    }

    @Test
    public void fruitTransaction_operation_getOperationByValue_s_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("s");
        assertEquals(expected, actual);
    }

    @Test
    public void fruitTransaction_operation_getOperationByValue_p_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("p");
        assertEquals(expected, actual);
    }

    @Test
    public void fruitTransaction_operation_getOperationByValue_r_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("r");
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void fruitTransaction_operation_getOperationByValue_Empty() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("");
    }

    @Test
    public void fruitTransaction_equals_OK() {
        FruitTransaction expected = transaction;
        FruitTransaction actual = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 10);;
        assertTrue(expected.equals(actual) && actual.equals(expected));
    }

    @Test
    public void fruitTransaction_hashCode_OK() {
        FruitTransaction expected = transaction;
        FruitTransaction actual = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 10);;
        assertTrue(expected.hashCode() == actual.hashCode());
    }

    @Test
    public void fruitTransaction_FruitTransaction_OK() {
        FruitTransaction expected = transaction;
        FruitTransaction actual = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 10);
        assertTrue(expected.equals(actual));
    }

    @Test
    public void fruitTransaction_getFruit_OK() {
        String expected = "apple";
        String actual = transaction.getFruit();
        assertEquals(expected, actual);
    }

    @Test
    public void fruitTransaction_getQuantity_OK() {
        int expected = 10;
        int actual = transaction.getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    public void fruitTransaction_getOperation_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = transaction.getOperation();
        assertEquals(expected, actual);
    }
}
