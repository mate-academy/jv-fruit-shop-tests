package core.basesyntax.service;

import core.basesyntax.model.Operation;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileDataValidatorTest {

    private static FileDataValidator fileDataValidator;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileDataValidator = new FileDataValidator();
    }

    @Test
    public void fileDataValidatorEmptyName_RuntimeException() {
        exceptionRule.expect(RuntimeException.class);
        Operation operation = new Operation();
        fileDataValidator.accept(operation);
    }

    @Test
    public void fileDataValidatorQuantityLessThanZero_RuntimeException() {
        exceptionRule.expect(RuntimeException.class);
        Operation operation = new Operation();
        operation.setFruitName("banana");
        operation.setQuantity(-15);
        fileDataValidator.accept(operation);
    }

    @Test
    public void fileDataValidatorWithEmptyFruitName_ExceptionThrown() {
        String expectedMessage = "Validation data didn't pass! Data is incorrect!";
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(expectedMessage);
        Operation operation = new Operation();
        operation.setFruitName("");
        fileDataValidator.accept(operation);
    }

    @Test
    public void fileDataValidatorCorrectData_NoException() {
        Operation operation = new Operation();
        operation.setFruitName("banana");
        operation.setQuantity(10);
        fileDataValidator.accept(operation);
    }
}
