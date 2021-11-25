package core.basesyntax.shop.service.impl;

import core.basesyntax.shop.service.Validator;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorImplTest {
    @Test
    public void validate_correctData_ok() {
        Validator validator = new ValidatorImpl();
        String correctData = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "b,durian,8\n"
                + "p,apple,20\n"
                + "r,apple,10\n"
                + "p,banana,5\n"
                + "s,banana,50\n"
                + "b,pear,50\n"
                + "p,pear,10";
        Assert.assertTrue(validator.validate(correctData));
    }

    @Test
    public void validate_corruptedData_notOk() {
        Validator validator = new ValidatorImpl();
        String corruptedData = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "b,durian,8\n"
                + "p,,20\n"
                + "r,apple,10\n"
                + "p,5\n"
                + "s,banana,50\n"
                + "b,pear,50\n"
                + "p,pear,10";
        try {
            validator.validate(corruptedData);
        } catch (Exception e) {
            return;
        }
        Assert.fail("Runtime exception was expected");
    }

    @Test
    public void validate_invalidData_notOk() {
        Validator validator = new ValidatorImpl();
        String invalidData = "Phlebas the Phoenician, a fortnight dead,\n"
                + "Forgot the cry of gulls, and the deep seas swell\n"
                + "And the profit and loss.\n"
                + "                          A current under sea\n"
                + "Picked his bones in whispers. As he rose and fell\n"
                + "He passed the stages of his age and youth\n"
                + "Entering the whirlpool.\n"
                + "                          Gentile or Jew\n"
                + "O you who turn the wheel and look to windward,\n"
                + "Consider Phlebas, who was once handsome and tall as you.";
        try {
            validator.validate(invalidData);
        } catch (Exception e) {
            return;
        }
        Assert.fail("Runtime exception was expected");
    }
}
