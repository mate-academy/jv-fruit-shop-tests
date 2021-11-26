package core.basesyntax.service.impl;

import core.basesyntax.exception.ValidatorException;
import core.basesyntax.service.Validator;
import java.util.List;
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
    public void validate_emptyList_notOk() {
        List<String> actual = List.of();
        validator.validate(actual);
    }
    
    @Test
    public void validate_titleLine_ok() {
        List<String> actual = List.of(TITLE_LINE);
        validator.validate(actual);
    }

    @Test
    public void validate_acceptablePattern_ok() {
        List<String> actual = List.of(VALID_STRING);
        validator.validate(actual);
    }

    @Test
    public void validate_lineWithQuantityZero_ok() {
        validator.validate(List.of("b,banana,0"));
    }

    @Test(expected = ValidatorException.class)
    public void validate_unacceptableOperation_notOk() {
        validator.validate(List.of("q,pineapple,200"));
    }

    @Test(expected = ValidatorException.class)
    public void validate_fruitNameWithWhitespace_notOk() {
        validator.validate(List.of("b,pine apple,200"));
    }

    @Test(expected = ValidatorException.class)
    public void validate_unacceptableQuantity_notOk() {
        validator.validate(List.of("b,pineapple,-200"));
    }

    @Test(expected = ValidatorException.class)
    public void validate_extraComma_notOk() {
        validator.validate(List.of("b,pineapple,0,2"));
    }

    @Test(expected = ValidatorException.class)
    public void validate_unacceptableNumber_notOk() {
        validator.validate(List.of("b,pineapple,0.2"));
    }

    @Test(expected = ValidatorException.class)
    public void validate_AlphabeticalQuantity_notOk() {
        validator.validate(List.of("b,pineapple,two"));
    }

    @Test(expected = ValidatorException.class)
    public void validate_OperationInUpperCase_notOk() {
        validator.validate(List.of("B,pineapple,two"));
    }

    @Test(expected = ValidatorException.class)
    public void validate_fruitNameInUpperCase_notOk() {
        validator.validate(List.of("b,Pineapple,two"));
    }
}
