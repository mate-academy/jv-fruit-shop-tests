package core.basesyntax.service.validator;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

public class ValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test(expected = RuntimeException.class)
    public void validate_inCorrectData_NotOk() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "apple,100",
                "s,banana100"
        );
        validator.validate(data);
    }

    @Test(expected = NullPointerException.class)
    public void validate_nullData_NotOk() {
        validator.validate(null);
    }

    @Test
    public void validate_correctData_Ok() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100",
                "s,banana,100"
        );
        validator.validate(data);
    }
}
