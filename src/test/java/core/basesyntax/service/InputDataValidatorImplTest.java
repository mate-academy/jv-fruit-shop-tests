package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import service.InputDataValidator;

public class InputDataValidatorImplTest {
    private InputDataValidator validator;
    private List<String> input;

    @Test
    public void validator_ok() {
        input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        validator = new InputDataValidatorImpl();
        boolean actual = validator.validate(input);
        boolean expected = true;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void input_is_empty() throws RuntimeException {
        input = new ArrayList<>();
        validator = new InputDataValidatorImpl();
        try {
            validator.validate(input);
        } catch (RuntimeException e) {
            Assert.assertEquals("invalid data", e.getMessage());
        }
    }

    @Test
    public void input_operation_invalid() {
        input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("f,banana,20");
        input.add("b,apple,100");
        validator = new InputDataValidatorImpl();
        try {
            validator.validate(input);
        } catch (RuntimeException e) {
            Assert.assertEquals("invalid operation at line № 2", e.getMessage());
        }
    }

    @Test
    public void input_fruit_name_invalid() {
        input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,b1n1na,20");
        input.add("b,apple,100");
        validator = new InputDataValidatorImpl();
        try {
            validator.validate(input);
        } catch (RuntimeException e) {
            Assert.assertEquals("invalid fruit name at line № 2", e.getMessage());
        }
    }

    @Test
    public void input_quantity_invalid() {
        input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,banana,2a0b");
        input.add("b,apple,100");
        validator = new InputDataValidatorImpl();
        try {
            validator.validate(input);
        } catch (RuntimeException e) {
            Assert.assertEquals("invalid quantity at line № 2", e.getMessage());
        }
    }

    @Test
    public void input_first_line_invalide() {
        input = new ArrayList<>();
        input.add("b,banana,2a0b");
        input.add("b,apple,100");
        validator = new InputDataValidatorImpl();
        try {
            validator.validate(input);
        } catch (RuntimeException e) {
            Assert.assertEquals("invalid data", e.getMessage());
        }
    }
}
