package service.impl;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import service.Validator;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validator_lineIsValid_Ok() {
        assertTrue(validator.validate("b,banana,100"));
    }

    @Test (expected = RuntimeException.class)
    public void validator_lineWithoutOperation_notOk() {
        validator.validate(",banana,100");
    }

    @Test (expected = RuntimeException.class)
    public void validator_lineWithTwoValidOperations_notOk() {
        validator.validate("br,banana,100");
    }

    @Test(expected = RuntimeException.class)
    public void validator_lineIsNull_notOk() {
        validator.validate(null);
    }

    @Test(expected = RuntimeException.class)
    public void validator_lineIsEmpty_notOk() {
        validator.validate("");
    }

    @Test (expected = RuntimeException.class)
    public void validator_lineWithNotExistOperation_notOk() {
        validator.validate("t,banana,100");
    }

    @Test (expected = RuntimeException.class)
    public void validator_lineWithoutFruitName_notOk() {
        validator.validate("b,,100");
    }

    @Test (expected = RuntimeException.class)
    public void validator_lineWithWrongFruitName_notOk() {
        validator.validate("b,banana2,100");
    }

    @Test (expected = RuntimeException.class)
    public void validator_lineWithoutQuantity_notOk() {
        validator.validate("b,banana,");
    }

    @Test (expected = RuntimeException.class)
    public void validator_lineWithNegativeQuantity_notOk() {
        validator.validate("b,banana,-100");
    }

    @Test (expected = RuntimeException.class)
    public void validator_lineWithWrongQuantity_notOk() {
        validator.validate("b,banana,banana100");
    }

    @Test (expected = RuntimeException.class)
    public void validator_lineIsLongerThanExpected_notOk() {
        validator.validate("b,banana,100,r");
    }
}
