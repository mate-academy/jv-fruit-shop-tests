package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.DataValidator;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidatorImplTest {
    private static DataValidator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new DataValidatorImpl();
    }

    @Test(expected = RuntimeException.class)
    public void validator_nullString_notOk() {
        validator.validate(null);
    }

    @Test(expected = RuntimeException.class)
    public void validator_emptyString_notOk() {
        validator.validate("");
    }

    @Test(expected = RuntimeException.class)
    public void validator_stringLessThanNumberColumns_notOk() {
        validator.validate("sda,asf");
    }

    @Test(expected = RuntimeException.class)
    public void validator_stringMoreThanNumberColumns_notOk() {
        validator.validate("sda,asf,dfasf,324");
    }

    @Test(expected = RuntimeException.class)
    public void validator_operationEmpty_notOk() {
        validator.validate(",asf,324");
    }

    @Test(expected = RuntimeException.class)
    public void validator_operationExistInUpperCase_notOk() {
        validator.validate("S,asf,324");
    }

    @Test(expected = RuntimeException.class)
    public void validator_operationNotExist_notOk() {
        validator.validate("q,asf,324");
    }

    @Test(expected = RuntimeException.class)
    public void validator_operationEmptyFruit_notOk() {
        validator.validate("p,,324");
    }

    @Test(expected = RuntimeException.class)
    public void validator_operationEmptyQuantity_notOk() {
        validator.validate("p,as,");
    }

    @Test(expected = RuntimeException.class)
    public void validator_operationQuantityWithLetter_notOk() {
        validator.validate("p,as,45t");
    }

    @Test(expected = RuntimeException.class)
    public void validator_operationQuantityChars_notOk() {
        validator.validate("p,as,#%");
    }

    @Test(expected = RuntimeException.class)
    public void validator_operationQuantityBelowZero_notOk() {
        validator.validate("p,as,-50");
    }

    @Test
    public void validator_operationWithValidData_ok() {
        boolean actual = validator.validate("b,apple,0");
        assertTrue(actual);
        actual = validator.validate("s,apple,50");
        assertTrue(actual);
        actual = validator.validate("p,apple,5");
        assertTrue(actual);
        actual = validator.validate("r,apple,250");
        assertTrue(actual);
    }
}
