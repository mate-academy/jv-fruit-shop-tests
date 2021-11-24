package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorImplTest {
    private List<String> lines;
    private final Validator validator = new ValidatorImpl();

    @Before
    public void setUp() {
        lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
    }

    @Test
    public void validate_ValidDate_Ok() {
        lines.add("b,banana,20");
        lines.add("s,banana,100");
        lines.add("p,banana,13");
        lines.add("r,banana,10");
        Assert.assertTrue(validator.validate(lines));
    }

    @Test(expected = RuntimeException.class)
    public void validate_InvalidDate_NotOk() {
        lines.add("b,banana,10,q");
        validator.validate(lines);
    }

    @Test(expected = RuntimeException.class)
    public void validate_InvalidNumber_NotOk() {
        lines.add("b,banana,-10");
        validator.validate(lines);
    }

    @Test(expected = RuntimeException.class)
    public void validate_InvalidOperation_NotOk() {
        lines.add("a,banana,20");
        validator.validate(lines);
    }

}
