package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidatorImplTest {
    private static Validator validator;
    private static List<String> file;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void validate_validFile_Ok() {
        Assert.assertTrue(validator.validate(file));
    }

    @Test
    public void validate_addInvalidLine_NotOk() {
        file.add("xd,xd,xd");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validate_invalidOperationType_NotOk() {
        file.set(1,"0,banana,20");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validate_invalidQuantity_NotOk() {
        file.set(1,"p,banana,^!@!");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validate_negativeQuantity_NotOk() {
        file.set(1,"p,banana,-10");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validate_invalidFruitName_NotOk() {
        file.set(1,"p,@ppl3,20");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validate_nullLine_NotOk() {
        file.set(1, null);
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validate_emptyLine_NotOk() {
        file.set(1, "        ");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }
}
