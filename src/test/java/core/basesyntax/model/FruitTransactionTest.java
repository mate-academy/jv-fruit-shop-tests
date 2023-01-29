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
    public void getOperationByValueTest_isBalance_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("b");
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByValueTest_isSupply_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("s");
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByValueTest_isPurchase_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("p");
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByValueTest_IsReturn_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("r");
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getOperationByValueTest_EmptyData_OK() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperationByValue("");
    }

    @Test
    public void equalsTest_EqualsMatch_OK() {
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
    public void getFruitTest_IsDataGet_OK() {
        String expected = "apple";
        String actual = transaction.getFruit();
        assertEquals(expected, actual);
    }

    @Test
    public void getQuantityTest_IsDataGet_OK() {
        int expected = 10;
        int actual = transaction.getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_isDataGet_OK() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = transaction.getOperation();
        assertEquals(expected, actual);
    }
}
