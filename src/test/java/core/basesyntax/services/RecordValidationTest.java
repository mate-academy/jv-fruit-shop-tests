package core.basesyntax.services;

import org.junit.Assert;
import org.junit.Test;

public class RecordValidationTest {
    private final RecordValidation recordValidation = new RecordValidation();

    @Test
    public void validateCorrectString_Ok() {
        String[] testList = new String[]{"operationName", "fruitName", "20"};
        Assert.assertTrue(recordValidation.test(testList));
    }

    @Test()
    public void validateWrongOperationName_NotOk() {
        String[] testList = new String[]{"", "fruitName", "20"};
        Assert.assertFalse(recordValidation.test(testList));
    }

    @Test()
    public void validateWrongFruitName_NotOk() {
        String[] testList = new String[]{"operationName", "", "20"};
        Assert.assertFalse(recordValidation.test(testList));
    }

    @Test()
    public void validateWrongQuantity_NotOk() {
        String[] testList = new String[]{"operationName", "fruitName", ""};
        Assert.assertFalse(recordValidation.test(testList));
    }

    @Test()
    public void validateNegativeQuantity_NotOk() {
        String[] testList = new String[]{"asdasd", "fruitName", "-20"};
        Assert.assertFalse(recordValidation.test(testList));
    }

    @Test()
    public void validateEmptyList_NotOk() {
        String[] testList = new String[]{};
        Assert.assertFalse(recordValidation.test(testList));
    }
}
