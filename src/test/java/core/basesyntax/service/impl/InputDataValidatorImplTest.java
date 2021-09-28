package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InputDataValidatorImplTest {
    private static InputDataValidatorImpl inputDataValidator;
    private static final List<String> VALID_INPUT = new ArrayList<>();

    @Before
    public void initialize() {
        inputDataValidator = new InputDataValidatorImpl();
        VALID_INPUT.add("b,banana,20");
        VALID_INPUT.add("b,lemon,10");
    }

    @Test
    public void checkData_Ok() {
        assertTrue(inputDataValidator.chekDate(VALID_INPUT));
    }
}
