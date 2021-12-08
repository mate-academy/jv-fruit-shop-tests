package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InputDataValidatorImplTest {
    private static final String INPUT_HEAD = "type,fruit,quantity";
    private static InputDataValidatorImpl inputDataValidator;
    private static final List<String> validInput = new ArrayList<>();
    private static final List<String> notValidInput = new ArrayList<>();

    @Before
    public void initialize() {
        inputDataValidator = new InputDataValidatorImpl();
    }

    @Test
    public void checkData_Ok() {
        validInput.add(INPUT_HEAD);
        validInput.add("b,banana,20");
        validInput.add("b,lemon,10");
        assertTrue(inputDataValidator.chekDate(validInput));
    }

    @Test
    public void checkThrow() {
        notValidInput.add(INPUT_HEAD);
        notValidInput.add("b,banana,");
        assertThrows(RuntimeException.class, () -> inputDataValidator.chekDate(notValidInput));
    }
}
