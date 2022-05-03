package core.basesyntax.service.validator;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
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

    @Test
    public void validate_correctData_Ok() {
        List<String> data = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,100",
                "s,banana,100"
        );
        boolean actual = validator.validate(data);
        Assert.assertTrue(actual);
    }
}
