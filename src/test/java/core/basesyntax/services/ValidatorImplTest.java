package core.basesyntax.services;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ValidatorImplTest {
    private static ValidatorImpl validator = new ValidatorImpl();

    @Test
    public void validate_length_isNotOk() {
        String[] strings = new String[]{"b","banana", "3", "odd"};
        Assertions.assertThrows(RuntimeException.class, () ->
                    validator.validate(strings));
    }

    @Test
    public void validate_operation_isNotOk() {
        String[] strings = new String[]{"k","banana", "3"};
        Assertions.assertThrows(RuntimeException.class, () ->
                    validator.validate(strings));
    }

    @Test
    public void validate_quantity_isNotOk() {
        String[] strings = new String[]{"b","banana", "-89", };
        Assertions.assertThrows(RuntimeException.class, () ->
                    validator.validate(strings));
    }
}
