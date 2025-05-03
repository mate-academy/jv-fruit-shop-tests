package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;
    private static List<String> inputDataFromFile;
    private boolean actual;

    @BeforeClass
    public static void beforeClass() throws Exception {
        validator = new ValidatorImpl();
        inputDataFromFile = new ArrayList<>();
    }

    @Test
    public void isDataValid_ok() {
        inputDataFromFile.add("b,banana,120");
        inputDataFromFile.add("r,apple,20");
        actual = validator.isDataValid(inputDataFromFile);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isDataValid_wrongLength_notOk() {
        inputDataFromFile.add("r,apple,120,kg,tasty");
        inputDataFromFile.add("r,apple,");
        validator.isDataValid(inputDataFromFile);
    }

    @Test
    public void checkValidOfAmount_positiveAmount_ok() {
        inputDataFromFile.add("b,banana,10");
        inputDataFromFile.add("r,apple,15");
        actual = validator.checkValidOfAmount(inputDataFromFile);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void checkValidOfAmount_negativeAmount_notOk() {
        inputDataFromFile.add("b,banana,-10");
        inputDataFromFile.add("r,apple,-15");
        validator.checkValidOfAmount(inputDataFromFile);
    }

    @After
    public void tearDown() throws Exception {
        inputDataFromFile.clear();
    }
}
