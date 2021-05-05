package core.basesyntax.validate.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.validate.RemoveOperationValidator;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemoveOperationValidatorImplTest {
    private static RemoveOperationValidator removeOperationValidator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        removeOperationValidator = new RemoveOperationValidatorImpl();
    }

    @Test
    public void removeValidationTest_Ok() {
        Integer firstTest = 20;
        Integer secondTest = 15;
        assertTrue(removeOperationValidator.removeOperationValidate(firstTest,secondTest));
    }

    @Test (expected = RuntimeException.class)
    public void removeValidationWithIncorrectParamsTest_NotOk() {
        removeOperationValidator.removeOperationValidate(5,10);
    }
}
