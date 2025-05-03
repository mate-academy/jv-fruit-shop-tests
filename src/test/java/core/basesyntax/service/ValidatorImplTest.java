package core.basesyntax.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ValidatorImplTest {
    private final Validator validator = new ValidatorImpl();

    @Test
    public void correctString_Ok() {
        List<String> text = Arrays.asList("operationName,fruitName,200");
        validator.validate(text);
    }

    @Test(expected = RuntimeException.class)
    public void wrongString_NotOk() {
        List<String> text = Arrays.asList(",fruitName,5");
        validator.validate(text);
    }

    @Test(expected = RuntimeException.class)
    public void emptyList() {
        List<String> listEmpty = new ArrayList<>();
        validator.validate(listEmpty);
    }
}
