package core.basesyntax.services.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.TransactionDto;
import org.junit.Test;

public class ValidateDataImplTest {
    @Test(expected = ValidationException.class)
    public void isDataOk_EmptyString_NotOk() {
        String record = "";
        ValidateData validateData = new ValidateDataImpl();
        validateData.isDataOk(record);
        fail("Must will be an Exception!");
    }

    @Test(expected = NullPointerException.class)
    public void isDataOk_StringWithIncorrectFirstParameter_NotOk() {
        String record = "c,fruit,12";
        ValidateData validateData = new ValidateDataImpl();
        validateData.isDataOk(record);
        fail("Must will be an Exception!");
    }

    @Test(expected = NumberFormatException.class)
    public void isDataOk_StringWithIncorrectThirdParameter_NotOk() {
        String record = "b,fruit,twelve";
        ValidateData validateData = new ValidateDataImpl();
        validateData.isDataOk(record);
        fail("Must will be an Exception!");
    }

    @Test(expected = ValidationException.class)
    public void isDataOk_StringWithThirdParameterBelowZero_NotOk() {
        String record = "b,fruit,-65";
        ValidateData validateData = new ValidateDataImpl();
        validateData.isDataOk(record);
        fail("Must will be an Exception!");
    }

    @Test(expected = ValidationException.class)
    public void isDataOk_StringInIncorrectFormat_NotOk() {
        String record = "b, ";
        ValidateData validateData = new ValidateDataImpl();
        validateData.isDataOk(record);
        fail("Must will be an Exception!");
    }

    @Test
    public void isDataOk_StringInCorrectFormat_Ok() {
        String record = "b,banana,12";
        TransactionDto expected = new TransactionDto(
                "b", "banana", 12);
        ValidateData validateData = new ValidateDataImpl();
        TransactionDto actual = validateData.isDataOk(record);
        assertEquals(expected, actual);
    }
}
