package core.basesyntax.dao;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {
    private Validator validator = new Validator();
    private String validPath = "src/main/java/resources/inputFile.csv";
    private boolean thrown = false;

    @Test
    public void checkThreeParameters_Ok() {
        try {
            validator.checkData(validPath);
        } catch (RuntimeException e) {
            thrown = true;
        }
        Assert.assertFalse("Each infoLine should has 3 parameters "
                        + "and value of operation mus be greater than zero",
                thrown);
    }
}
