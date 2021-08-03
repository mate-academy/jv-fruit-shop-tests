package core.basesyntax;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import validators.FruitDataValidator;
import validators.FruitDataValidatorCsvImpl;

public class FruitDataValidatorCsvImplTest {
    private static final FruitDataValidator validator = new FruitDataValidatorCsvImpl();

    @Test
    public void validate_Ok() {
        List<String> rows = new ArrayList<>();
        rows.add("b,grape,50");
        rows.add("p,melon,30");
        rows.add("r,apple,100");
        rows.add("s,orange,8");
        Assert.assertTrue(validator.validate(rows));
    }

    @Test
    public void validate_NotOk() {
        List<String> rows = new ArrayList<>();
        rows.add("b,grape,50");
        rows.add("i,melon,30");
        Assert.assertFalse(validator.validate(rows));

        rows.clear();
        rows.add("b,grape,50");
        rows.add("p,grape,-30");
        Assert.assertFalse(validator.validate(rows));

        rows.clear();
        rows.add("b,grape,50");
        rows.add("p,,30");
        Assert.assertFalse(validator.validate(rows));

        rows.clear();
        rows.add("b,grape,50");
        rows.add("p,30");
        Assert.assertFalse(validator.validate(rows));
    }
}
