package core.basesyntax;

import core.basesyntax.service.DataValidation;
import core.basesyntax.service.impl.DataValidationImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidationImplTest {
    private static final List<String> EMPTY = new ArrayList<>();
    private static final List<String> NOT_EMPTY = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10");
    private static final String VALID_STRING = "b,banana,15";
    private static final String FIRST_PART_INVALID_STRING = "q,apple,14";
    private static final String SECOND_PART_INVALID_STRING = "r,ih&&e,10";
    private static final String THIRD_PART_INVALID_STRING = "s,apple,14$";
    private static final String FULL_INVALID_STRING = "q, kjh**, 18$";
    private static final String EMPTY_STRING = "";
    private static DataValidation dataValidation;
    private boolean actual;
    private boolean expected;
    private int currentQuantity;
    private int subtract;

    @BeforeClass
    public static void initialization() {
        dataValidation = new DataValidationImpl();
    }

    @Test
    public void checkListNotEmpty_notEmptyList_ok() {
        expected = true;
        actual = dataValidation.checkListNotEmpty(NOT_EMPTY);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void checkListNotEmpty_emptyList_notOk() {
        dataValidation.checkListNotEmpty(EMPTY);
    }

    @Test
    public void checkLine_validString_ok() {
        expected = true;
        actual = dataValidation.checkLine(VALID_STRING);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void checkLine_firstPartInvalidString_notOk() {
        dataValidation.checkLine(FIRST_PART_INVALID_STRING);
    }

    @Test(expected = RuntimeException.class)
    public void checkLine_secondPartInvalidString_notOk() {
        dataValidation.checkLine(SECOND_PART_INVALID_STRING);
    }

    @Test(expected = RuntimeException.class)
    public void checkLine_thirdPartInvalidString_notOk() {
        dataValidation.checkLine(THIRD_PART_INVALID_STRING);
    }

    @Test(expected = RuntimeException.class)
    public void checkLine_fullInvalidString_notOk() {
        dataValidation.checkLine(FULL_INVALID_STRING);
    }

    @Test(expected = RuntimeException.class)
    public void checkLine_emptyString_notOk() {
        dataValidation.checkLine(EMPTY_STRING);
    }

    @Test
    public void subtractCheck_validInput_ok() {
        currentQuantity = 5;
        subtract = 3;
        expected = true;
        actual = dataValidation.removeCheck(currentQuantity, subtract);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void subtractCheck_invalidInput_notOk() {
        currentQuantity = 3;
        subtract = 5;
        dataValidation.removeCheck(currentQuantity, subtract);
    }
}
