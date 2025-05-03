package core.basesyntax.service;

import core.basesyntax.service.impl.ValidatorImpl;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorImplTest {
    private static final Validator validator = new ValidatorImpl();

    @Test
    public void isValid_validateNormalData_Ok() {
        List<String> data = List.of("b,banana,15");
        Assert.assertTrue(validator.isValid(data));
    }

    @Test(expected = RuntimeException.class)
    public void isValid_emptyData_notOk() {
        List<String> data = Collections.emptyList();
        validator.isValid(data);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_invalidOperation_notOk() {
        List<String> data = List.of("a,banana,15");
        validator.isValid(data);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_invalidFormatOfData_notOk() {
        List<String> data = List.of("p,");
        validator.isValid(data);
    }

    @Test(expected = RuntimeException.class)
    public void isValid_negativeNumber_notOk() {
        List<String> data = List.of("p,banana,-15");
        validator.isValid(data);
    }
}
