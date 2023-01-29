package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void getOperationByValueTest_Balance_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("b");
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByValueTest_Supply_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("s");
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByValueTest_Purchase_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("p");
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByValueTest_Return_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("r");
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getOperationByValueTest_EmptyData_OK() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("");
    }

    @Test
    public void equalsTest_EqualMatch_OK() {
        FruitTransaction expected = transaction;
        FruitTransaction actual = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 10);;
        assertTrue(expected.equals(actual) && actual.equals(expected));
    }

    @Test
    public void hashCodeTest_HashCodeMatch_OK() {
        FruitTransaction expected = transaction;
        FruitTransaction actual = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 10);;
        assertTrue(expected.hashCode() == actual.hashCode());
    }

    @Test
    public void fruitTransactionTest_ConstructorInit_OK() {
        FruitTransaction expected = transaction;
        FruitTransaction actual = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 10);
        assertTrue(expected.equals(actual));
    }

    @Test
    public void getFruitTest_GetData_OK() {
        String expected = "apple";
        String actual = transaction.getFruit();
        assertEquals(expected, actual);
    }

    @Test
    public void getQuantityTest_GetData_OK() {
        int expected = 10;
        int actual = transaction.getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_GetData_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = transaction.getOperation();
        assertEquals(expected, actual);
    }
}
