package core.basesyntax;

import core.basesyntax.validator.Validator;
import core.basesyntax.validator.ValidatorImpl;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static ArrayList<String> invalidInput1;
    private static ArrayList<String> invalidInput2;
    private Validator validator = new ValidatorImpl();

    @BeforeClass
    public static void beforeAll() {
        invalidInput1 = new ArrayList<>();
        invalidInput1.add("type,fruit,quantity");
        invalidInput1.add("b,banana,150");
        invalidInput1.add("b,apple,100");
        invalidInput1.add("s,banana,");
        invalidInput2 = new ArrayList<>();
        invalidInput2.add("type,fruit,quantity");
        invalidInput2.add("b,banana,150");
        invalidInput2.add("b,apple,100");
        invalidInput2.add("s,banana,-20");
    }

    @Test
    public void incorrectDataFromFile() {
        boolean expected = false;
        boolean actual = validator.validate(invalidInput1);
        Assert.assertEquals("Data validation is incorrect (no number in input file",
                expected, actual);
    }

    @Test
    public void incorrectDataFromFileNegativeNumber() {
        boolean expected = false;
        boolean actual = validator.validate(invalidInput2);
        Assert.assertEquals("Data validation is incorrect (number is negative",
                expected, actual);
    }
}
