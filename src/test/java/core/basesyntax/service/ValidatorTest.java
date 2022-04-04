package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.ValidatorImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static Validator validator;
    private static List<String> data;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_validInput_Ok() {
        data = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10");
        assertTrue(validator.validate(data));
    }

    @Test
    public void validator_onlyFirstLine_Ok() {
        data = List.of("type,fruit,quantity");
        assertTrue(validator.validate(data));
    }

    @Test
    public void validator_validPattern_Ok() {
        data = List.of("type,fruit,quantity", "r,cream,80");
        assertTrue(validator.validate(data));
    }

    @Test (expected = NullPointerException.class)
    public void validator_nullData_NotOk() {
        validator.validate(null);
    }

    @Test (expected = RuntimeException.class)
    public void validator_emptyData_NokOk() {
        validator.validate(List.of());
    }

    @Test (expected = RuntimeException.class)
    public void validator_typeNotValid_notOk() {
        data = List.of("u,banana,20");
        validator.validate(data);
    }

    @Test (expected = RuntimeException.class)
    public void validator_negativeQuantity_NotOk() {
        data = List.of("b,banana,-90");
        validator.validate(data);
    }

    @Test (expected = RuntimeException.class)
    public void validator_fruitNotValid_NotOk() {
        data = List.of("r,Banana,0");
        validator.validate(data);
    }
}
