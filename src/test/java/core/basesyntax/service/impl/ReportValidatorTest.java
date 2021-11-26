package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportValidatorTest {
    private static List<String> inputData;
    private static Validator validator;

    @BeforeClass
    public static void init() {
        inputData = new ArrayList<>();
        validator = new ReportValidator();
    }

    @Test
    public void testValidInputData_Ok() {
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,34");
        Assert.assertTrue(validator.test(inputData));
    }

    @Test(expected = RuntimeException.class)
    public void testInputDataWithoutTitleLine_notOk() {
        inputData.add("b,banana,34");
        inputData.add("s,apple,40");
        inputData.add("p,banana,1");
        validator.test(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void testInputDataWithNegativeValue_notOk() {
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,34");
        inputData.add("s,apple,-37");
        inputData.add("p,banana,45");
        validator.test(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void testInputDataWithEmptyData_notOk() {
        inputData.add("type,fruit,quantity");
        inputData.add("b,banana,34");
        inputData.add("s,,34");
        inputData.add("p,banana,34");
        validator.test(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void testInputDataWithInvalidTitleLine_notOk() {
        inputData.add("t,f,q");
        inputData.add("b,banana,34");
        inputData.add("s,apple,34");
        inputData.add("p,banana,34");
        validator.test(inputData);
    }

    @After
    public void resetList() {
        inputData.clear();
    }
}
