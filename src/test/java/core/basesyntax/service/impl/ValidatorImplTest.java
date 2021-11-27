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
    public void validator_validFile_Ok() {
        Assert.assertTrue(validator.validate(file));
    }

    @Test
    public void validator_addInvalidLine_NotOk() throws RuntimeException {
        file.add("xd,xd,xd");
        thrown.expectMessage("Format of file is incorrect");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validator_invalidOperationType_NotOk() throws RuntimeException {
        file.set(1,"0,banana,20");
        thrown.expectMessage("Format of file is incorrect");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validator_invalidQuantity_NotOk() throws RuntimeException {
        file.set(1,"p,banana,^!@!");
        thrown.expectMessage("Format of file is incorrect");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validator_negativeQuantity_NotOk() throws RuntimeException {
        file.set(1,"p,banana,-10");
        thrown.expectMessage("Format of file is incorrect");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validator_invalidFruitName_NotOk() throws RuntimeException {
        file.set(1,"p,@ppl3,20");
        thrown.expectMessage("Format of file is incorrect");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validator_nullLine_NotOk() throws RuntimeException {
        file.set(1, null);
        thrown.expectMessage("Format of file is incorrect");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }

    @Test
    public void validator_emptyLine_NotOk() throws RuntimeException {
        file.set(1, "        ");
        thrown.expectMessage("Format of file is incorrect");
        thrown.expect(RuntimeException.class);
        validator.validate(file);
    }
}
