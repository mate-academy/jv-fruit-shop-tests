package core.basesyntax.service;

import core.basesyntax.exception.ValidationException;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static Validator validator;
    private static List<String> inputData;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test(expected = RuntimeException.class)
    public void validateData_nullInputData_notOk() {
        validator.validate(null);
    }

    @Test(expected = ValidationException.class)
    public void validateData_onlyOneLine_notOk() {
        inputData = List.of("b,banana,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validateData_emptyList_notOk() {
        inputData = List.of();
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validateData_dataWithoutFirstLine_notOk() {
        inputData = List.of("b,banana,100", "b,apple,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validateData_dataWithWrongHeadLine_notOk() {
        inputData = List.of("type", "b,banana,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validateData_dataWithNegativeValue_notOk() {
        inputData = List.of("type,fruit,quantity", "b,banana,-20");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validateData_dataWithDoubleValue_notOk() {
        inputData = List.of("type,fruit,quantity", "b,banana,0.7");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validateData_dataWithExtraComma_notOk() {
        inputData = List.of("type,fruit,quantity", "b,banana,0,7", "b,apple,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validateData_dataWithWrongType_notOk() {
        inputData = List.of("type,fruit,quantity", "q,banana,100", "w,apple,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validateData_dataWithUpperCaseType_notOk() {
        inputData = List.of("type,fruit,quantity", "B,banana,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validateData_dataWithWrongFruitName_notOk() {
        inputData = List.of("type,fruit,quantity", "b,1banana,100");
        validator.validate(inputData);
    }

    @Test(expected = ValidationException.class)
    public void validateData_dataWithUpperCaseFruitName_notOk() {
        inputData = List.of("type,fruit,quantity", "b,Banana,100");
        validator.validate(inputData);
    }

    @Test
    public void validateData_dataWithZeroValue_Ok() {
        inputData = List.of("type,fruit,quantity", "b,banana,0", "b,apple,0");
        boolean actual = validator.validate(inputData);
        Assert.assertTrue(actual);
    }

    @Test
    public void validateData_validData_ok() {
        inputData = List.of("type,fruit,quantity", "b,banana,100", "b,apple,100");
        boolean actual = validator.validate(inputData);
        Assert.assertTrue(actual);
    }
}
