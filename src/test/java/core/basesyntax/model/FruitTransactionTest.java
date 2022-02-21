package core.basesyntax.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {
    private static final String BAD_LETTER = "l";
    private static final String LETTER_B = "b";
    private static final String LETTER_P = "p";
    private static final String LETTER_S = "s";
    private static final String LETTER_R = "r";
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void getOperationByLetter_b_ok() {
        FruitTransaction.Operation actual = fruitTransaction.getOperationByLetter(LETTER_B);
        Assert.assertEquals(FruitTransaction.Operation.BALANCE, actual);
    }

    @Test
    public void getOperationByLetter_p_ok() {
        FruitTransaction.Operation actual = fruitTransaction.getOperationByLetter(LETTER_P);
        Assert.assertEquals(FruitTransaction.Operation.PURCHASE, actual);
    }

    @Test
    public void getOperationByLetter_s_ok() {
        FruitTransaction.Operation actual = fruitTransaction.getOperationByLetter(LETTER_S);
        Assert.assertEquals(FruitTransaction.Operation.SUPPLY, actual);
    }

    @Test
    public void getOperationByLetter_r_ok() {
        FruitTransaction.Operation actual = fruitTransaction.getOperationByLetter(LETTER_R);
        Assert.assertEquals(FruitTransaction.Operation.RETURN, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationByBadLetter_notOk() {
        FruitTransaction.Operation actual = fruitTransaction.getOperationByLetter(BAD_LETTER);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationByNull_notOk() {
        FruitTransaction.Operation actual = fruitTransaction.getOperationByLetter(null);
    }
}
