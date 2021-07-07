package core.basesyntax.service.impl;

import core.basesyntax.service.DataValidatorService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataValidatorServiceImplTest {
    private static DataValidatorService validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new DataValidatorServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkDataInput_sizeData_notOk() {
        int size = 2;
        String[] data = new String[size];
        validator.checkDataInput(data);
    }

    @Test
    public void validator_checkDataInput_BalanceOperation_PositiveQuality_ok() {
        String[] data = new String[]{"b", "banana", "10"};
        boolean expected = true;
        boolean actual = validator.checkDataInput(data);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validator_checkDataInput_SupplyOperation_PositiveQuality_ok() {
        String[] data = new String[]{"s", "banana", "10"};
        boolean expected = true;
        boolean actual = validator.checkDataInput(data);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validator_checkDataInput_PurchaseOperation_PositiveQuality_ok() {
        String[] data = new String[]{"p", "banana", "10"};
        boolean expected = true;
        boolean actual = validator.checkDataInput(data);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validator_checkDataInput_ReturnOperation_PositiveQuality_ok() {
        String[] data = new String[]{"r", "banana", "10"};
        boolean expected = true;
        boolean actual = validator.checkDataInput(data);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkDataInput_IncorrectOperation_notOk() {
        String[] data = new String[]{"H", "banana", "10"};
        validator.checkDataInput(data);
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkDataInput_emptyFruit_notOk() {
        String[] data = new String[]{"r", "", "10"};
        validator.checkDataInput(data);
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkDataInput_blankFruit_notOk() {
        String[] data = new String[]{"r", " ", "10"};
        validator.checkDataInput(data);
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkDataInput_negativeQuantity_notOk() {
        String[] data = new String[]{"r", "apple", "-10"};
        validator.checkDataInput(data);
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkOperation_negativeValue_notOk() {
        validator.checkOperation(-10);
    }

    @Test
    public void validator_checkOperation_positiveValue_ok() {
        boolean expected = true;
        boolean actual = validator.checkOperation(10);
        Assert.assertEquals(expected, actual);
    }
}
