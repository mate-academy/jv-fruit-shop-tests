package core.basesyntax.services.implementation;

import core.basesyntax.services.Validator;
import org.junit.Test;

public class ValidatorImplTest {
    private static final String VALUE_WITH_WRONG_LENGTH = "b,apple,red,20";
    private static final String VALUE_WITH_WRONG_QUANTITY = "b,apple,-20";
    private static final Validator validator = new ValidatorImpl();

    @Test (expected = RuntimeException.class)
    public void shouldThrowRunTimeExceptionWhenValueNull_Not_Ok() {
        validator.validate(null);
    }

    @Test (expected = RuntimeException.class)
    public void shouldThrowRunTimeExceptionWhenWrongLength_Not_Ok() {
        validator.validate(VALUE_WITH_WRONG_LENGTH);
    }

    @Test (expected = RuntimeException.class)
    public void shouldThrowRunTimeExceptionWhenValueLessThanZero_Not_Ok() {
        validator.validate(VALUE_WITH_WRONG_QUANTITY);
    }
}
