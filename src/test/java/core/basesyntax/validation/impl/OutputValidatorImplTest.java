package core.basesyntax.validation.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OutputValidatorImplTest {
    private static OutputValidatorImpl outputValidator;

    @BeforeAll
    static void setUp() {
        outputValidator = new OutputValidatorImpl();
    }


    @Test
    void validFileValidation_Ok() {
        String validFileForTest = "src/test/java/resources/OutputValidatorValidTest.csv";
        outputValidator.validateFile(validFileForTest);
        Assertions.assertTrue(outputValidator.validateFile(validFileForTest));
    }

    @Test
    void fileInvalid_NotOk() {
        String invalidFileForTest = "src/test/java/resources/OutputValidatorInvalidTest.csv";
        Assert.assertThrows(RuntimeException.class,
                () -> outputValidator.validateFile(invalidFileForTest));
    }

    @Test
    void invalidQuantity_NotOk() {
        String invalidQuantityFile = "src/test/java/resources/FileWithInvalidQuantity.csv";
        Assert.assertThrows(RuntimeException.class,
                () -> outputValidator.validateFile(invalidQuantityFile));
    }

    @Test
    void noDataInFile_NotOk() {
        String noDataFile  = "src/test/java/resources/NullFile.csv";
        Assert.assertThrows(RuntimeException.class,
                () -> outputValidator.validateFile(noDataFile));
    }
}
