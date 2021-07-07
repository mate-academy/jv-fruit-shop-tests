package core.basesyntax.service.validator;

import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test
    public void parse_Ok() {
        validator.validate("b,banana,20");
    }

    @Test(expected = RuntimeException.class)
    public void parse_NotOk() {
        validator.validate(",a");
    }
}
