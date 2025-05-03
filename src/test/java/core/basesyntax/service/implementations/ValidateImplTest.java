package core.basesyntax.service.implementations;

import core.basesyntax.service.inerfaces.Validate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidateImplTest {
    private static Validate validator;

    @BeforeClass
    public static void beforeAll() {
        validator = new ValidateImpl();
    }

    @Test (expected = RuntimeException.class)
    public void validate_Null_NotOk() {
        validator.validate(null);
    }

    @Test (expected = RuntimeException.class)
    public void validate_Empty_NotOk() {
        validator.validate(new ArrayList<>());
    }

    @Test (expected = RuntimeException.class)
    public void validate_FirstRowCheck_NotOk() {
        List<String> input = List.of("b,banana,20");
        validator.validate(input);
    }

    @Test (expected = RuntimeException.class)
    public void validate_WrongOperation_NotOk() {
        List<String> input = List.of("t,banana,20");
        validator.validate(input);
    }

    @Test (expected = RuntimeException.class)
    public void validate_WrongFruitName_NotOk() {
        List<String> input = List.of("b,1an2n1,20");
        validator.validate(input);
    }

    @Test (expected = RuntimeException.class)
    public void validate_WrongNumber_NotOk() {
        List<String> input = List.of("b,banana,-20");
        validator.validate(input);
    }

    @Test
    public void validate_ValidInput_Ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"
        );
        Assert.assertTrue(validator.validate(input));
    }
}
