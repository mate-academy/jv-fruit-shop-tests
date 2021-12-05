package core.basesyntax.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ValidatorImplTest {
    private final Validator validator = new ValidatorImpl();

    @Test
    public void validateCorrectString_Ok() {
        List<String> testList = Arrays.asList("operationName,fruitName,10");
        validator.validate(testList);
    }

    @Test(expected = RuntimeException.class)
    public void validateWrongString_NotOk() {
        List<String> testList = Arrays.asList(",fruitName,10");
        validator.validate(testList);
    }

    @Test(expected = RuntimeException.class)
    public void validateEmptyList() {
        List<String> emptyList = new ArrayList<>();
        validator.validate(emptyList);
    }
}
