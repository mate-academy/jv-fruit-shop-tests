package core.basesyntax.service.impl;

import core.basesyntax.exception.ValidatorException;
import core.basesyntax.service.Validator;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;
    private static final String TITLE_LINE = "type,fruit,quantity";
    private static final String VALID_STRING = "b,pineapple,200";

    @BeforeClass
    public static void setUp() {
        validator = new ValidatorImpl();
    }

    @Test(expected = ValidatorException.class)
    public void list_isEmpty_notOk() {
        List<String> actual = List.of();
        validator.validate(actual);
    }
    
    @Test
    public void validator_firstLine_isValid_ok() {
        List<String> actual = List.of(TITLE_LINE);
        validator.validate(actual);
    }

    @Test
    public void validator_linePattern_ok() {
        List<String> actual = List.of(VALID_STRING);
        validator.validate(actual);
    }

    @Test
    public void validator_quantityZero_ok() {
        validator.validate(List.of("b,banana,0"));
    }

    @Test(expected = ValidatorException.class)
    public void validator_typeInvalid_notOk() {
        validator.validate(List.of("q,pineapple,200"));
    }

    @Test(expected = ValidatorException.class)
    public void validator_FruitWhitespace_notOk() {
        validator.validate(List.of("b,pine apple,200"));
    }

    @Test(expected = ValidatorException.class)
    public void validator_quantityMinus_notOk() {
        validator.validate(List.of("b,pineapple,-200"));
    }

    @Test(expected = ValidatorException.class)
    public void validator_ExtraComma_notOk() {
        validator.validate(List.of("b,pineapple,0,2"));
    }

    @Test(expected = ValidatorException.class)
    public void validator_quantityDouble_notOk() {
        validator.validate(List.of("b,pineapple,0.2"));
    }

    @Test(expected = ValidatorException.class)
    public void validator_quantityAlphabetically_notOk() {
        validator.validate(List.of("b,pineapple,two"));
    }

    @Test(expected = ValidatorException.class)
    public void validator_typeUpperCase_notOk() {
        validator.validate(List.of("B,pineapple,two"));
    }

    @Test(expected = ValidatorException.class)
    public void validator_fruitUpperCase_notOk() {
        validator.validate(List.of("b,Pineapple,two"));
    }
}
