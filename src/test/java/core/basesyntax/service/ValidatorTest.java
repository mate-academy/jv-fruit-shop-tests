package core.basesyntax.service;

import core.basesyntax.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static Validator validator;
    private static List<String> inputData;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Before
    public void beforeEach() {
        inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,0");
        inputData.add("b,apple,0");
    }

    @Test(expected = RuntimeException.class)
    public void validate_nullInputData_notOk() {
        validator.validate(null);
    }

    @Test(expected = ValidationException.class)
    public void validate_onlyOneLine_notOk() {
        inputData = List.of("b,banana,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validate_emptyList_notOk() {
        inputData = List.of();
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validate_dataWithoutFirstLine_notOk() {
        inputData = List.of("b,banana,100", "b,apple,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validate_dataWithWrongHeadLine_notOk() {
        inputData = List.of("type", "b,banana,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validate_dataWithNegativeValue_notOk() {
        inputData.add("b,banana,-20");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validate_dataWithDoubleValue_notOk() {
        inputData.add("b,banana,0.7");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validate_dataWithExtraComma_notOk() {
        inputData.add("b,banana,0,7");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validate_dataWithWrongType_notOk() {
        inputData.add("w,apple,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validate_dataWithUpperCaseType_notOk() {
        inputData.add("B,banana,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validate_dataWithWrongFruitName_notOk() {
        inputData.add("b,1banana,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validate_dataWithUpperCaseFruitName_notOk() {
        inputData.add("b,Banana,100");
        validator.validate(inputData);
    }

    @Test
    public void validate_dataWithZeroValue_ok() {
        inputData.add("b,banana,0");
        boolean actual = validator.validate(inputData);
        Assert.assertTrue(actual);
    }

    @Test
    public void validate_validData_ok() {
        boolean actual = validator.validate(inputData);
        Assert.assertTrue(actual);
    }

}
