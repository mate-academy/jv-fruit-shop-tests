package core.basesyntax.model;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    public static final String BALANCE_LETTER = "b";
    public static final String SUPPLY_LETTER = "s";
    public static final String RETURN_LETTER = "r";
    public static final String PURCHASE_LETTER = "p";
    public static final String UNEXPECTED_LETTER = "i";
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() throws Exception {
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void getOperationLetter_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                fruitTransaction.getOperationLetter(BALANCE_LETTER));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                fruitTransaction.getOperationLetter(SUPPLY_LETTER));
        assertEquals(FruitTransaction.Operation.RETURN,
                fruitTransaction.getOperationLetter(RETURN_LETTER));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                fruitTransaction.getOperationLetter(PURCHASE_LETTER));
    }

    @Test
    public void getOperationWrongLetter_notOk() {
        assertThrows(RuntimeException.class,
                () -> fruitTransaction.getOperationLetter(UNEXPECTED_LETTER));

    }
}
