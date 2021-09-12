package core.basesyntax;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.validator.Validator;
import core.basesyntax.validator.ValidatorImp;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {
    private static Validator validator;
    private static final String correctActivity = "b,banana,20";
    private static final String wrongLengthActivityOne = "banana";
    private static final String wrongLengthActivityTwo = "b,b,banana,20,g,h";
    private static final String wrongQuantityOne = "b,banana,null";
    private static final String wrongQuantityTwo = "b,banana,-20";
    private static final String wrongActivityCodes = "k,banana,20";
    private static final String[] testInput = new String[] {"b,banana,20",
            "s,banana,100", "p,banana,100", "r,banana,100"};

    @Before
    public void initialize() {
        validator = new ValidatorImp();
    }

    @Test
    public void validator_All_True() {
        boolean actual = validator.validationReportLine(correctActivity);
        assertTrue(actual);
    }

    @Test
    public void validator_Null_False() {
        boolean actual = validator.validationReportLine(null);
        assertFalse(actual);
    }

    @Test
    public void validator_ArrayLength_False() {
        boolean actual = validator.validationReportLine(wrongLengthActivityOne);
        assertFalse(actual);
        actual = validator.validationReportLine(wrongLengthActivityTwo);
        assertFalse(actual);
    }

    @Test
    public void validator_CheckQuantity_False() {
        boolean actual = validator.validationReportLine(wrongQuantityOne);
        assertFalse(actual);
        actual = validator.validationReportLine(wrongQuantityTwo);
        assertFalse(actual);
    }

    @Test
    public void validator_ActivityCodes_False() {
        boolean actual = validator.validationReportLine(wrongActivityCodes);
        assertFalse(actual);
    }
}
