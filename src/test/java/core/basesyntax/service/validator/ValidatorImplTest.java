package core.basesyntax.service.validator;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test
    public void test_parse_Ok() {
        validator.validate("b,banana,20");
    }

    @Test
    public void test_parse_NotOk() {
        try {
            validator.validate(",a");
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }
}
