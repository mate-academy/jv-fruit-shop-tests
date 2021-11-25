package core.basesyntax.service.impl;

import core.basesyntax.exception.ValidateException;
import core.basesyntax.service.Validator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorCsvImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        validator = new ValidatorCsvImpl();
    }

    @Test(expected = ValidateException.class)
    public void validateAction_nullLine_notOk() {
        validator.validateLine(null);
    }

    @Test(expected = ValidateException.class)
    public void validateAction_wrongAction_notOk() {
        String line = "a,banana,20";
        validator.validateLine(line);
    }

    @Test(expected = ValidateException.class)
    public void validateAction_nullAction_ok() {
        String line = ",banana,20";
        validator.validateLine(line);
    }

    @Test
    public void validateAction_rightAction_ok() {
        String line = "b,banana,20";
        validator.validateLine(line);
    }

    @Test(expected = ValidateException.class)
    public void validateAction_wrongName_notOk() {
        String line = "a,%banana,20";
        validator.validateLine(line);
    }

    @Test(expected = ValidateException.class)
    public void validateAction_nullName_ok() {
        String line = "b,,20";
        validator.validateLine(line);
    }

    @Test
    public void validateAction_rightName_ok() {
        String line = "b,banana,20";
        validator.validateLine(line);
    }

    @Test(expected = ValidateException.class)
    public void validateAmount_wrongAmount_notOk() {
        String line = "b,banana,#20";
        validator.validateLine(line);
    }

    @Test(expected = ValidateException.class)
    public void validateAmount_nullAmount_notOk() {
        String line = "b,banana, ";
        validator.validateLine(line);
    }

    @Test
    public void validateAmount_rightAmount_ok() {
        String line = "b,banana,10";
        validator.validateLine(line);
    }
}
