package core.basesyntax.services.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.TransactionDto;
import org.junit.Test;

public class ValidateDataImplTest {
    @Test
    public void isDataOk_EmptyString_NotOk() {
        String record = "";
        boolean haveException = false;
        ValidateData validateData = new ValidateDataImpl();
        try {
            TransactionDto transactionDto = validateData.isDataOk(record);
        } catch (ValidationException e) {
            haveException = true;
        }
        assertTrue(haveException);
    }

    @Test
    public void isDataOk_StringWithIncorrectFirstParameter_NotOk() {
        String record = "c,fruit,12";
        boolean haveException = false;
        ValidateData validateData = new ValidateDataImpl();
        try {
            TransactionDto transactionDto = validateData.isDataOk(record);
        } catch (NullPointerException e) {
            haveException = true;
        }
        assertTrue(haveException);
    }

    @Test
    public void isDataOk_StringWithIncorrectThirdParameter_NotOk() {
        String record = "b,fruit,twelve";
        boolean haveException = false;
        ValidateData validateData = new ValidateDataImpl();
        try {
            TransactionDto transactionDto = validateData.isDataOk(record);
        } catch (NumberFormatException e) {
            haveException = true;
        }
        assertTrue(haveException);
    }

    @Test
    public void isDataOk_StringWithThirdParameterBelowZero_NotOk() {
        String record = "b,fruit,-65";
        boolean haveException = false;
        ValidateData validateData = new ValidateDataImpl();
        try {
            TransactionDto transactionDto = validateData.isDataOk(record);
        } catch (ValidationException e) {
            haveException = true;
        }
        assertTrue(haveException);
    }

    @Test
    public void isDataOk_StringInIncorrectFormat_NotOk() {
        String record = "b, ";
        boolean haveException = false;
        ValidateData validateData = new ValidateDataImpl();
        try {
            TransactionDto transactionDto = validateData.isDataOk(record);
        } catch (ValidationException e) {
            haveException = true;
        }
        assertTrue(haveException);
    }

    @Test
    public void isDataOk_StringInCorrectFormat_Ok() {
        String record = "b,banana,12";
        TransactionDto expected = new TransactionDto(
                "b", "banana", 12);
        ValidateData validateData = new ValidateDataImpl();
        TransactionDto actual = validateData.isDataOk(record);
        assertEquals(expected.getOperationType(), actual.getOperationType());
        assertEquals(expected.getFruit(), actual.getFruit());
        assertEquals(expected.getAmount(), actual.getAmount());
    }
}
