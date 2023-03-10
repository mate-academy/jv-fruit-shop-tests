package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TypeOfOperationTest {
    private static final String INVALID_CODE = "f";
    private static final String CODE_TO_BALANCE = "b";
    private static final String CODE_TO_PURCHASE = "p";
    private static final String CODE_TO_SUPPLY = "s";
    private static final String CODE_TO_RETURN = "r";

    @Test(expected = RuntimeException.class)
    public void getByCode_not_exists_code_notOk() {
        TypeOfOperation.getByCode(INVALID_CODE);
        fail("Should throw an exception with invalid input parameter");
    }

    @Test(expected = RuntimeException.class)
    public void getByCode_empty_code_notOk() {
        TypeOfOperation.getByCode("");
        fail("Should throw an exception with empty input parameter");
    }

    @Test
    public void getByCode_valid_data_Ok() {
        TypeOfOperation expected1 = TypeOfOperation.BALANCE;
        TypeOfOperation actual1 = TypeOfOperation.getByCode(CODE_TO_BALANCE);
        assertEquals(expected1, actual1);

        TypeOfOperation expected2 = TypeOfOperation.SUPPLY;
        TypeOfOperation actual2 = TypeOfOperation.getByCode(CODE_TO_SUPPLY);
        assertEquals(expected2, actual2);

        TypeOfOperation expected3 = TypeOfOperation.PURCHASE;
        TypeOfOperation actual3 = TypeOfOperation.getByCode(CODE_TO_PURCHASE);
        assertEquals(expected3, actual3);

        TypeOfOperation expected4 = TypeOfOperation.RETURN;
        TypeOfOperation actual4 = TypeOfOperation.getByCode(CODE_TO_RETURN);
        assertEquals(expected4, actual4);
    }

    @Test(expected = RuntimeException.class)
    public void getByCode_with_null_parameter_notOk() {
        TypeOfOperation.getByCode(null);
        fail("Should throw an exception when input parameter is null");
    }
}
