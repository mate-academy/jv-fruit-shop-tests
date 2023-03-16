package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class FruitTransactionTest {
    private static final String CODE_FOR_TEST = "b";
    private static final String WRONG_FOR_TEST = "z";

    @Test
    public void getOperationByCode_Successful_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual
                = FruitTransaction.Operation.getOperationByCode(CODE_FOR_TEST);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getOperationByCode_NotSuccessful_NotOk() {
        FruitTransaction.Operation.getOperationByCode(WRONG_FOR_TEST);
        fail(RuntimeException.class.getName());
    }
}
