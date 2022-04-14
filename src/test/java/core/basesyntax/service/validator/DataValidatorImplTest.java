package core.basesyntax.service.validator;

import core.basesyntax.exception.ValidationException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidatorImplTest {
    private static DataValidator dataValidator;
    private static String CORRECT_DATA = "b,banana,20";
    private static String INCORRECT_DATA = "b,banana";
    private static String INCORRECT_VALUE = "b,banana,-20";

    @BeforeClass
    public static void setUp() {
        dataValidator = new DataValidatorImpl();
    }

    @Test
    public void correct_data_Ok() throws ValidationException {
        boolean expected = true;
        boolean actual = dataValidator.validate(CORRECT_DATA);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void value_null_Ok() {
        Assert.assertThrows(ValidationException.class,
                () -> dataValidator.validate(null));
    }

    @Test
    public void incorrect_data_Ok() {
        Assert.assertThrows(ValidationException.class,
                () -> dataValidator.validate(INCORRECT_DATA));
    }

    @Test
    public void incorrect_value_Ok() {
        Assert.assertThrows(ValidationException.class,
                () -> dataValidator.validate(INCORRECT_VALUE));
    }
}
