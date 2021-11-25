package core.basesyntax.service;

import core.basesyntax.service.impl.ValidatorImpl;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorImplTest {
    private static final Validator VALIDATOR = new ValidatorImpl();

    @Test
    public void validData_Ok() {
        List<String> data = List.of("b,banana,15");
        Assert.assertTrue(VALIDATOR.isValid(data));
    }

    @Test(expected = RuntimeException.class)
    public void emptyData_notOk() {
        List<String> data = Collections.emptyList();
        VALIDATOR.isValid(data);
    }

    @Test(expected = RuntimeException.class)
    public void invalidOperation_notOk() {
        List<String> data = List.of("a,banana,15");
        VALIDATOR.isValid(data);
    }

    @Test(expected = RuntimeException.class)
    public void invalidFormatOfData() {
        List<String> data = List.of("p,");
        VALIDATOR.isValid(data);
    }

    @Test(expected = RuntimeException.class)
    public void negativeNumber_notOk() {
        List<String> data = List.of("p,banana,-15");
        VALIDATOR.isValid(data);
    }
}
