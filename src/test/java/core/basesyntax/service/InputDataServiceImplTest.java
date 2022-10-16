package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class InputDataServiceImplTest {
    private static InputDataService inputDataService;

    @BeforeClass
    public static void beforeClass() {
        inputDataService = new InputDataServiceImpl();
    }

    @Test
    public void stringToFruitTransactionConverter_validInputTextFormat_ok() {
        List<String> validInputTextList = List.of(
                "b,pineapple,10",
                "r,banana,100",
                "p,orange,50");
        Integer expectedNumberOfInputLines = 3;
        Integer actualNumberOfInputLines =
                inputDataService.stringToFruitTransactionConverter(validInputTextList).size();
        assertEquals(expectedNumberOfInputLines, actualNumberOfInputLines);
    }

    @Test
    public void stringToFruitTransactionConverter_invalidDataFormatExceptionMessage_ok() {
        String invalidLineOne = "pineapple,10";
        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidLineOne));
            fail("Input Data has invalid format exception should be thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + invalidLineOne, e.getMessage());
        }
    }

    @Test
    public void stringToFruitTransactionConverter_inputEmptyLineExceptionMessage_ok() {
        String emptyLine = "";
        String nullLine = new String();
        try {
            inputDataService.stringToFruitTransactionConverter(List.of(emptyLine));
            fail("Input Data has invalid format exception should be thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + emptyLine, e.getMessage());
        }
    }

    @Test
    public void stringToFruitTransactionConverter_inputNullLineExceptionMessage_ok() {
        String nullLine = new String();
        try {
            inputDataService.stringToFruitTransactionConverter(List.of(nullLine));
            fail("Input Data has invalid format exception should be thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + nullLine, e.getMessage());
        }
    }

    @Test
    public void stringToFruitTransactionConverter_invalidLetterOperationExceptionMessage_ok() {
        String invalidOperationLetter = "m,pineapple,10";
        String[] invalidOperationLetterArray = invalidOperationLetter.split(",");
        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidOperationLetter));
            fail("Unknown operation, should throw exception");
        } catch (RuntimeException e) {
            assertEquals("Unknown operation: " + invalidOperationLetterArray[0], e.getMessage());
        }
    }

    @Test
    public void stringToFruitTransactionConverter_invalidNumberOperationExceptionMessage_ok() {
        String invalidOperationNumber = "9,pineapple,10";
        String[] invalidOperationNumberArray = invalidOperationNumber.split(",");
        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidOperationNumber));
            fail("Unknown operation, should throw exception");
        } catch (RuntimeException e) {
            assertEquals("Unknown operation: " + invalidOperationNumberArray[0], e.getMessage());
        }
    }
}
