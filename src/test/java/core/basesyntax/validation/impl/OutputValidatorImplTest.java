package core.basesyntax.validation.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OutputValidatorImplTest {
    private final String invalidFileForTest = "OutputValidatorinValidTest.csv";
    private final String invalidQuantityFile = "resources/FileWithInvalidQuantity.csv";
    private final OutputValidatorImpl outputValidator = new OutputValidatorImpl();

    @Test
    void fileValid_Ok() {
        String validFileForTest = "src/test/java/resources/OutputValidatorValidTest.csv";
        outputValidator.validateFile(validFileForTest);
        Assertions.assertTrue(outputValidator.validateFile(validFileForTest));
    }

    @Test
    void fileInvalid_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> outputValidator.validateFile(invalidFileForTest));
    }

    @Test
    void invalidQuantity_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> outputValidator.validateFile(invalidQuantityFile));
    }
}
