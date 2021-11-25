
package core.basesyntax.services.impl;

import core.basesyntax.exceptions.LineParseException;
import core.basesyntax.services.Validator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_validLine_Ok() {
        validator.validate("b,apple,20");
    }

    @Test
    public void validate_shortFruitName_Ok() {
        validator.validate("b,a,20");
    }

    @Test(expected = LineParseException.class)
    public void validate_notDigitalOperation_NotOk() {
        validator.validate("0,apple,20");
    }

    @Test(expected = LineParseException.class)
    public void validate_noOperation_NotOk() {
        validator.validate(",apple,20");
    }

    @Test(expected = LineParseException.class)
    public void validate_noFruitName_NotOk() {
        validator.validate("b,,20");
    }

    @Test(expected = LineParseException.class)
    public void validate_noQuantity_NotOk() {
        validator.validate("b,apple,");
    }

    @Test(expected = LineParseException.class)
    public void validate_noDelimiter_NotOk() {
        validator.validate("bapple20");
        validator.validate("bapple,20");
        validator.validate("b,apple20");
    }

    @Test(expected = LineParseException.class)
    public void validate_emptyLine_NotOk() {
        validator.validate("");
    }

    @Test(expected = LineParseException.class)
    public void validate_null_NotOk() {
        validator.validate(null);
    }

    @Test(expected = LineParseException.class)
    public void validate_negativeQuantity_NotOk() {
        validator.validate("b,apple,-1");
    }

    @Test(expected = LineParseException.class)
    public void validate_additionalSymbols_NotOk() {
        validator.validate("b,apple,10 ");
        validator.validate(" b,apple,10");
        validator.validate("b,apple,10 // 123");
    }
}
