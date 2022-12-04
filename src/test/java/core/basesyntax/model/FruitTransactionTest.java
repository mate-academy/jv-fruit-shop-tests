package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.Test;

public class FruitTransactionTest {
    private static String FRUIT = "apple";
    private static String OPERATION_CODE = "b";
    private static String ILLIGAL_OPERATION_CODE = "w";
    private static Operation TEST_BALANCE_OPERATION = FruitTransaction.Operation.BALANCE;
    private FruitTransaction fruitTransaction;

    @Test
    public void getExistingOperation_ok() {
        fruitTransaction = new FruitTransaction(TEST_BALANCE_OPERATION, FRUIT, 20);
        Operation actual = fruitTransaction.getOperation();
        Operation expected = TEST_BALANCE_OPERATION;
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByCode_ok() {
        Operation actual = FruitTransaction.Operation.getByCode(OPERATION_CODE);
        Operation expected = TEST_BALANCE_OPERATION;
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOperationByCodeIllegal_notOk() {
        fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.getByCode(ILLIGAL_OPERATION_CODE), FRUIT, 20);
    }

    @Test
    public void getOperationCode_ok() {
        fruitTransaction = new FruitTransaction(TEST_BALANCE_OPERATION, FRUIT, 20);
        String actual = fruitTransaction.getOperation().getOperation();
        assertEquals(OPERATION_CODE, actual);
    }
    
    @Test
    public void isEqaualsObject_ok() {
        FruitTransaction actual = new FruitTransaction(TEST_BALANCE_OPERATION, FRUIT, 20);
        fruitTransaction = new FruitTransaction(TEST_BALANCE_OPERATION, FRUIT, 20);
        assertTrue(fruitTransaction.equals(actual));
    }
}
