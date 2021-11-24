package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static Predicate<String[]> validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new Validator();
    }

    @Test
    public void validatorTest_cuntOfDailyOperations_notOk() {
        String[] dailyOperations = new String[5];
        assertThrows(RuntimeException.class, () -> validator.test(dailyOperations));
    }

    @Test
    public void validatorTest_countOfProducts_notOK() {
        String[] notValidDailyOperations = {"b", "apple", "-5"};
        assertThrows(RuntimeException.class, () -> validator.test(notValidDailyOperations));
    }

    @Test
    public void validatorTest_Ok() {
        String[] validDailyOperations = {"b", "apple", "10"};
        assertTrue(validator.test(validDailyOperations));
    }
}
