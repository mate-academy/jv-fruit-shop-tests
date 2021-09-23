package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitRecordDto;
import org.junit.Test;

public class ValidatorTest {
    private static final String TYPE_BALANCE = "b";
    private static final String TYPE_PURCHASE = "p";
    private static final String CORRECT_FRUIT_NAME = "apple";
    private static final String EMPTY_STRING = "";
    private static final int NEGATIVE = -1;
    private static final int POSITIVE = 10;
    private static final int ZERO = 0;

    @Test
    public void checkType_correctType_Ok() {
        FruitRecordDto.Activities expected = FruitRecordDto.Activities.BALANCE;
        FruitRecordDto.Activities actual = Validator.checkType(TYPE_BALANCE);
        assertEquals(expected, actual);
    }

    @Test
    public void checkType_incorrectType_Ok() {
        FruitRecordDto.Activities expected = FruitRecordDto.Activities.BALANCE;
        FruitRecordDto.Activities actual = Validator.checkType(TYPE_PURCHASE);
        assertNotEquals(expected, actual);
    }

    @Test
    public void checkType_emptyString_Ok() {
        try {
            Validator.checkType(EMPTY_STRING);
        } catch (RuntimeException e) {
            return;
        }
        fail("should throw exception: 'This type of operation does not exist: + type'");
    }

    @Test
    public void checkFruitName_correctFruitName_Ok() {
        String expected = "apple";
        String actual = Validator.checkFruitName(CORRECT_FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void checkFruitName_incorrectFruitName_Ok() {
        String expected = "apple";
        String actual = Validator.checkFruitName(TYPE_BALANCE);
        assertNotEquals(expected, actual);
    }

    @Test
    public void checkFruitName_emptyString_Ok() {
        try {
            Validator.checkFruitName(EMPTY_STRING);
        } catch (RuntimeException e) {
            return;
        }
        fail("should throw exception: 'Fruit name is NOT listed");

    }

    @Test
    public void checkAmount_incorrect_Ok() {
        try {
            Validator.checkAmount(NEGATIVE);
        } catch (RuntimeException e) {
            return;
        }
        fail("should throw exception: 'Value cannot be negative. Your value is + amount'");
    }

    @Test
    public void checkAmount_correct_Ok() {
        int expected = 10;
        int actual = Validator.checkAmount(POSITIVE);
        assertEquals(expected,actual);
    }

    @Test
    public void checkAmount_zero_Ok() {
        int expected = 0;
        int actual = Validator.checkAmount(ZERO);
        assertEquals(expected,actual);
    }
}
