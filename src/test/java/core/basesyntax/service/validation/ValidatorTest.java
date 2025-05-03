package core.basesyntax.service.validation;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static Validator validator;
    private static List<String> actual;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorCsvImpl();
        actual = new ArrayList<>();
    }

    @Before
    public void setUp() {
        actual.add("Some info that will be skipped");
        actual.add("b,banana,20");
        actual.add("b,apple,100");
        actual.add("s,banana,100");
        actual.add("p,banana,13");
    }

    @After
    public void tearDown() {
        actual.clear();
    }

    @Test
    public void validData_Ok() {
        Assert.assertTrue(validator.isValid(actual));
    }

    @Test(expected = RuntimeException.class)
    public void notValidLine_NotOk() {
        actual.add("ddddd");
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void notValidUpperCaseOperation_NotOk() {
        actual.add("B,banana,10");
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void notValidUpperCaseFruit_NotOk() {
        actual.add("b,Banana,10");
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void notValidFruit_NotOk() {
        actual.add("b,banana!,10");
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void notValidOperation_NotOk() {
        actual.add("z,banana,10");
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void notValidNegativeAmount_NotOk() {
        actual.add("s,banana,-8");
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void notValidZeroAmount_NotOk() {
        actual.add("s,apple,0");
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void notValidDoubleAmount_NotOk() {
        actual.add("s,apple,2.2");
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void emptyLine_NotOk() {
        actual.add("");
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void oneLineList_NotOk() {
        actual.clear();
        actual.add("Some info that will be skipped");
        validator.isValid(actual);
    }

    @Test
    public void lessIndexesLine_NotOk() {
        List<String> notValidLength = new ArrayList<>();
        notValidLength.add("some info");
        notValidLength.add("b,apple");
        int expected = 3;
        String[] line = notValidLength.get(1).split(",");
        int actual = line.length;
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void moreIndexesLine_NotOk() {
        List<String> notValidLength = new ArrayList<>();
        notValidLength.add("some info");
        notValidLength.add("b,apple,100,vvv");
        int expected = 3;
        String[] line = notValidLength.get(1).split(",");
        int actual = line.length;
        Assert.assertNotEquals(expected, actual);
    }
}
