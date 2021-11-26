package core.service.impl;

import core.service.ValidatorService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorServiceImplTest {
    private List<String> lines;
    private ValidatorService validator;

    @Before
    public void setUp() {
        validator = new ValidatorServiceImpl();
        lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
    }

    @Test
    public void validate_correctWork_ok() {
        lines.add("b,banana,21");
        lines.add("s,banana,100");
        lines.add("p,banana,16");
        lines.add("r,banana,10");
        Assert.assertTrue(validator.validate(lines));
    }

    @Test(expected = RuntimeException.class)
    public void validate_invalidDate_notOk() {
        lines.add("b,banana,10,pog");
        validator.validate(lines);
    }

    @Test(expected = RuntimeException.class)
    public void validate_invalidQuantity_notOk() {
        lines.add("b,banana,-17");
        validator.validate(lines);
    }

    @Test(expected = RuntimeException.class)
    public void validate_invalidOperation_notOk() {
        lines.add("u,banana,20");
        validator.validate(lines);
    }
}
