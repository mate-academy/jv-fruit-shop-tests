package core.basesyntax.service.impl;

import core.basesyntax.service.CsvValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvValidatorImplTest {
    private static CsvValidator csvValidator;
    private String dataInput;

    @BeforeClass
    public static void beforeClass() {
        csvValidator = new CsvValidatorImpl();
    }

    @Test
    public void validate_validDataInput_Ok() {
        dataInput = "type,fruit,quantity\n"
                + "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n"
                + "p,banana,13\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                + "p,banana,5\n"
                + "s,banana,50\n";
        Assert.assertTrue(csvValidator.validate(dataInput));
    }

    @Test
    public void validate_validPattern_Ok() {
        dataInput = "type,fruit,quantity\n" + "b,banana,20\n";
        Assert.assertTrue(csvValidator.validate(dataInput));
    }

    @Test(expected = NullPointerException.class)
    public void validate_nullInputData_NotOk() {
        csvValidator.validate(null);
    }

    @Test(expected = RuntimeException.class)
    public void validate_emptyLine_NotOk() {
        csvValidator.validate("");
    }

    @Test(expected = RuntimeException.class)
    public void validate_unknownOperation_NotOk() {
        dataInput = "l,apple,100";
        csvValidator.validate(dataInput);
    }

    @Test(expected = RuntimeException.class)
    public void validate_invalidFruitName_NotOk() {
        dataInput = "b,Coconut,100";
        csvValidator.validate(dataInput);
    }

    @Test(expected = RuntimeException.class)
    public void validate_negativeQuantity_NotOk() {
        dataInput = "b,apple,-100";
        csvValidator.validate(dataInput);
    }
}
