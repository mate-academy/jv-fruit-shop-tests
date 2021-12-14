package core.basesyntax.validators;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidatorForFileTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testValidatorNoOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Not found operation or name of fruit!!!");
        ValidatorForFile validator = new ValidatorForFileImpl();
        validator.test("src/main/resources/noOperation.vcs");
    }

    @Test
    public void testValidatorCorrectNoOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("The quality must be greater than zero!!!");
        ValidatorForFile validator = new ValidatorForFileImpl();
        validator.test("src/main/resources/noCorrect.vcs");
    }

    @Test
    public void testValidatorOk() {
        Boolean expected = true;
        ValidatorForFile validator = new ValidatorForFileImpl();
        assertEquals(expected, validator.test("src/main/resources/10.09.99.vcs"));
    }

}
