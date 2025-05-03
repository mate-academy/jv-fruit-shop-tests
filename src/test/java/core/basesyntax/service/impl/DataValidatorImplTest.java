package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.DataValidator;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidatorImplTest {
    private static DataValidator dataValidator;

    @BeforeClass
    public static void setUp() {
        dataValidator = new DataValidatorImpl();
    }

    @Test
    public void validate_ok() {
        String[] typeActivity = {"b", "s", "r", "p"};
        for (String activity : typeActivity) {
            String string = activity + ",text," + "0123456789";
            assertTrue(dataValidator.validate(string));
        }
    }

    @Test (expected = RuntimeException.class)
    public void validateInvalid_notOk() {
        String invalidData = "d, cabbage1, two";
        dataValidator.validate(invalidData);
    }

    @Test (expected = RuntimeException.class)
    public void validateEmptyData_notOk() {
        dataValidator.validate("");
    }
}
