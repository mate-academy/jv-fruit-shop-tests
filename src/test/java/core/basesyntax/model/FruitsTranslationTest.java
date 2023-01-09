package core.basesyntax.model;

import static core.basesyntax.operation.Operation.BALANCE;
import static core.basesyntax.operation.Operation.getByCode;

import core.basesyntax.operation.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FruitsTranslationTest {
    private static final String TEST_FRUIT = "apple";
    private static final String TEST_OPERATION_CODE = "b";
    private static final String TEST_WRONG_OPERATION_CODE = "w";
    private static final int TEST_BALANCE = 50;
    private static final Operation TEST_OPERATION = BALANCE;
    private FruitsTranslation fruitTransaction;

    @Before
    public void setUp() {
        fruitTransaction = new FruitsTranslation(BALANCE, TEST_FRUIT, TEST_BALANCE);
        fruitTransaction.setOperation(TEST_OPERATION);
        fruitTransaction.setFruit(TEST_FRUIT);
        fruitTransaction.setQuantity(TEST_BALANCE);
    }

    @Test
    public void getByCode_Work_Ok() {
        Operation actual = getByCode(TEST_OPERATION_CODE);
        Assertions.assertEquals(TEST_OPERATION, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByCode_IllegalArgExc_notOk() {
        fruitTransaction.setOperation(getByCode(TEST_WRONG_OPERATION_CODE));
    }
}
