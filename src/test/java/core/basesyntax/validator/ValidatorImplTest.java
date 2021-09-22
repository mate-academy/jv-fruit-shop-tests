package core.basesyntax.validator;

import static org.junit.Assert.assertTrue;

import core.basesyntax.exception.ValidatorException;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator<String> validator;
    private static String correctRecord;
    private static String notFullRecord;
    private static String nullRecord;
    private static String emptyRecord;
    private static String recordWithWrongQuantityFieldType;

    @BeforeClass
    public static void setUp() {
        validator = new ValidatorImpl();
        correctRecord = "b,banana,20";
        notFullRecord = "b,banana";
        nullRecord = null;
        emptyRecord = "";
        recordWithWrongQuantityFieldType = "b,banana,NaN";
    }

    @Test
    public void validate_correctRecord_Ok() throws ValidatorException {
        assertTrue("Test failed! Record " + correctRecord + " isn`t valid",
                validator.validate(correctRecord));
    }

    @Test(expected = ValidatorException.class)
    public void validate_nullRecord_NotOk() throws ValidatorException {
        validator.validate(nullRecord);
    }

    @Test(expected = ValidatorException.class)
    public void validate_notFullRecord_NotOk() throws ValidatorException {
        validator.validate(notFullRecord);
    }

    @Test(expected = ValidatorException.class)
    public void validate_emptyRecord_NotOk() throws ValidatorException {
        validator.validate(emptyRecord);
    }

    @Test(expected = ValidatorException.class)
    public void validate_recordWithWrongTypeOfQuantityField_NotOk() throws ValidatorException {
        validator.validate(recordWithWrongQuantityFieldType);
    }
}
