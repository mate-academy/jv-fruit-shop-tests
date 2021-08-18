package core.basesyntax.service;

import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileDataValidatorTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private FileDataValidator fileDataValidator;

    @Before
    public void setUp() throws Exception {
        fileDataValidator = new FileDataValidator();
    }

    @Test(expected = RuntimeException.class)
    public void fileDataValidatorEmptyName_RuntimeException() {
        Operation operation = new Operation();
        fileDataValidator.accept(operation);
    }

    @Test(expected = RuntimeException.class)
    public void fileDataValidatorQuantityLessThanZero_RuntimeException() {
        Operation operation = new Operation();
        operation.setFruitName("banana");
        operation.setQuantity(-15);
        fileDataValidator.accept(operation);
    }

    @Test
    public void fileDataValidatorWithEmptyFruitName_ExceptionThrown() {
        exceptionRule.expect(RuntimeException.class);
        String expectedMessage = "Validation data didn't pass! Data is incorrect!";
        exceptionRule.expectMessage(expectedMessage);
        Operation operation = new Operation();
        operation.setFruitName("");
        fileDataValidator.accept(operation);
    }
}
