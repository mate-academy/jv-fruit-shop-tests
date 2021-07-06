package core.basesyntax.servicetest;

import core.basesyntax.service.DataValidator;
import core.basesyntax.service.impl.DataValidatorImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidatorImplTest {
    private static DataValidator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new DataValidatorImpl();
    }

    @Test
    public void isValid_RegularData_Ok() {
        List<String[]> list = List.of(new String[]{"r", "banana", "90"},
                new String[]{"s", "apple", "100"}, new String[]{"b", "grape", "1"});
        for (String[] element : list) {
            Assert.assertTrue(String.format("Expected true for data:\n%s\nbut was:\n",
                    Arrays.toString(element)), validator.isValid(element));
        }
    }

    @Test(expected = RuntimeException.class)
    public void isValid_fruitNameEmpty_NotOk() {
        String[] actual = new String[]{"r", "", "90"};
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_negativeQuantity_NotOk() {
        String[] actual = new String[]{"s", "apple", "-100"};
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_twoElementsInArray_NotOk() {
        String[] actual = new String[]{"grape", "1"};
        validator.isValid(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_nonParsableQuantity_NotOk() {
        String[] actual = new String[]{"b", "banana", "blabla"};
        validator.isValid(actual);
    }
}
