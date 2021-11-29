package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;
    private static List<String> file;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
        file = new ArrayList<>();
    }

    @Before
    public void beforeEach() {
        file.clear();
        file.addAll(List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"));
    }

    @Test
    public void validate_validFile_ok() {
        Assert.assertTrue(validator.validate(file));
    }

    @Test(expected = RuntimeException.class)
    public void validate_addInvalidLine_notOk() {
        file.add("xd,xd,xd");
        validator.validate(file);
    }

    @Test(expected = RuntimeException.class)
    public void validate_invalidOperationType_NotOk() {
        file.set(1,"0,banana,20");
        validator.validate(file);
    }

    @Test(expected = RuntimeException.class)
    public void validate_invalidQuantity_notOk() {
        file.set(1,"p,banana,^!@!");
        validator.validate(file);
    }

    @Test(expected = RuntimeException.class)
    public void validate_negativeQuantity_notOk() {
        file.set(1,"p,banana,-10");
        validator.validate(file);
    }

    @Test(expected = RuntimeException.class)
    public void validate_invalidFruitName_notOk() {
        file.set(1,"p,@ppl3,20");
        validator.validate(file);
    }

    @Test(expected = RuntimeException.class)
    public void validate_nullLine_notOk() {
        file.set(1, null);
        validator.validate(file);
    }

    @Test(expected = RuntimeException.class)
    public void validate_emptyLine_notOk() {
        file.set(1, "        ");
        validator.validate(file);
    }
}
