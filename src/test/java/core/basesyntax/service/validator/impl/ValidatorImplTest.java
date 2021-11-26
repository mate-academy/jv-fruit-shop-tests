package core.basesyntax.service.validator.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.validator.Validator;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        validator = new ValidatorImpl();
    }

    @Test
    public void isValid_validData_Ok() {
        List<String> inputData = List.of("s,apple,10", "b,apple,5");
        boolean actual = validator.isValid(inputData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_invalidStringLength_notOk() {
        List<String> inputData = List.of("s,fruit", "b,apple,5");
        boolean actual = validator.isValid(inputData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_invalidFruitName_notOk() {
        List<String> inputData = List.of("s,,10", "b,apple,5");
        boolean actual = validator.isValid(inputData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_invalidOperationType_notOk() {
        List<String> inputData = List.of("s,apple,10", "q,apple,5");
        boolean actual = validator.isValid(inputData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_invalidQuantity_notOk() {
        List<String> inputData = List.of("s,apple,10", "b,apple,-5");
        boolean actual = validator.isValid(inputData);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_emptyData_notOk() {
        List<String> inputData = List.of();
        boolean actual = validator.isValid(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_nullData_notOk() {
        List<String> inputData = List.of(null, null, null);
        boolean actual = validator.isValid(inputData);
    }
}
