package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.InputDataValidator;

public class InputDataValidatorImplTest {
    private static InputDataValidator validator;
    private List<String> input;

    @BeforeClass
    public static void init() {
        validator = new InputDataValidatorImpl();
    }

    @Before
    public void init_before() {
        input = new ArrayList<>();
    }

    @Test
    public void validator_ok() {
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        boolean actual = validator.validate(input);
        boolean expected = true;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void input_is_empty() throws RuntimeException {
        try {
            validator.validate(input);
        } catch (RuntimeException e) {
            Assert.assertEquals("invalid data", e.getMessage());
        }
    }

    @Test
    public void input_operation_invalid() {
        input.add("type,fruit,quantity");
        input.add("f,banana,20");
        input.add("b,apple,100");
        try {
            validator.validate(input);
        } catch (RuntimeException e) {
            Assert.assertEquals("invalid operation at line № 2", e.getMessage());
        }
    }

    @Test
    public void input_fruit_name_invalid() {
        input.add("type,fruit,quantity");
        input.add("b,b1n1na,20");
        input.add("b,apple,100");
        try {
            validator.validate(input);
        } catch (RuntimeException e) {
            Assert.assertEquals("invalid fruit name at line № 2", e.getMessage());
        }
    }

    @Test
    public void input_quantity_invalid() {
        input.add("type,fruit,quantity");
        input.add("b,banana,2a0b");
        input.add("b,apple,100");
        try {
            validator.validate(input);
        } catch (RuntimeException e) {
            Assert.assertEquals("invalid quantity at line № 2", e.getMessage());
        }
    }

    @Test
    public void input_first_line_invalide() {
        input.add("b,banana,2a0b");
        input.add("b,apple,100");
        try {
            validator.validate(input);
        } catch (RuntimeException e) {
            Assert.assertEquals("invalid data", e.getMessage());
        }
    }
}
