package core.basesyntax.service;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

    @Test
    public void testValidateRow() {
        Validator validator = new Validator();
        String activityRowOk = "b,apple,5";
        String activityRow2 = "b,apple,5,nnn";
        String activityRow4 = "b,apple";

        Assert.assertTrue(validator.validateRow(activityRowOk));
        Assert.assertFalse(validator.validateRow(activityRow2));
        Assert.assertFalse(validator.validateRow(activityRow4));
    }
}
