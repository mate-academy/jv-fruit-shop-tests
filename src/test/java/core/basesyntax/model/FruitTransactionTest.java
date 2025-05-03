package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.exception.FruitException;
import org.junit.Test;

public class FruitTransactionTest {
    private static final String BALANCE_CODE = "b";
    private static final String RETURN_CODE = "r";
    private static final String PURCHASE_CODE = "p";
    private static final String SUPPLY_CODE = "s";
    private static final String UNKNOWN_CODE = "u";
    private static final FruitTransaction.Operation TEST_OPERATION
            = FruitTransaction.Operation.BALANCE;
    private static final String TEST_FRUIT = "banana";
    private static final int TEST_AMOUNT = 1;

    @Test
    public void getOperationByCode_Ok_ValidValues() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getOperationByCode(BALANCE_CODE));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getOperationByCode(RETURN_CODE));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getOperationByCode(PURCHASE_CODE));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getOperationByCode(SUPPLY_CODE));
    }

    @Test(expected = FruitException.class)
    public void getOperationByCode_NotOk_unknownValue() {
        FruitTransaction.Operation.getOperationByCode(UNKNOWN_CODE);
    }

    @Test(expected = FruitException.class)
    public void getOperationByCode_NotOk_nullValue() {
        FruitTransaction.Operation.getOperationByCode(null);
    }

    @Test
    public void get_Ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(TEST_OPERATION,TEST_FRUIT,TEST_AMOUNT);
        assertEquals(TEST_OPERATION, fruitTransaction.getOperation());
        assertEquals(TEST_FRUIT, fruitTransaction.getFruit());
        assertEquals(TEST_AMOUNT, fruitTransaction.getAmount());
    }

    @Test
    public void equals_TrueOk() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(TEST_OPERATION, TEST_FRUIT,TEST_AMOUNT);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(TEST_OPERATION, TEST_FRUIT,TEST_AMOUNT);
        assertEquals(fruitTransaction1, fruitTransaction2);
    }

    @Test
    public void equals_TrueOk_ft1EqualsFt1() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(TEST_OPERATION, TEST_FRUIT,TEST_AMOUNT);
        assertEquals(fruitTransaction1, fruitTransaction1);
    }

    @Test
    public void equals_FalseOk_equalsNull() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(TEST_OPERATION, TEST_FRUIT,TEST_AMOUNT);
        assertNotEquals(null, fruitTransaction1);
    }

    @Test
    public void hashCode_Ok() {
        FruitTransaction fruitTransaction1 =
                new FruitTransaction(TEST_OPERATION, TEST_FRUIT,TEST_AMOUNT);
        FruitTransaction fruitTransaction2 =
                new FruitTransaction(TEST_OPERATION, TEST_FRUIT,TEST_AMOUNT);
        assertEquals(fruitTransaction1.hashCode(), fruitTransaction2.hashCode());
    }
}
