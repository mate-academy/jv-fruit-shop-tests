package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import org.junit.Test;

public class InputDataServiceImplTest {
    private final InputDataService inputDataService = new InputDataServiceImpl();
    @Test
    public void stringToFruitTransactionConverter_validInputTextFormat_ok() {
        String validLineOne = "b,pineapple,10";
        String validLineTwo = "r,banana,100";
        String validLineThree = "p,orange,50";
        List<String> validInputTextList = List.of(validLineOne, validLineTwo, validLineThree);
        assertEquals(3,
                inputDataService.stringToFruitTransactionConverter(validInputTextList).size());
    }

    @Test
    public void stringToFruitTransactionConverter_invalidDataFormatExceptionMessage_Ok() {
        String invalidLineOne = "pineapple,10";
        String invalidLineTwo = "r,100";
        String invalidLineThree = "p,orange";
        String emptyLine = "";

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidLineOne));
            fail("Input Data has invalid format exception should be thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + invalidLineOne, e.getMessage());
        }

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidLineTwo));
            fail("Input Data has invalid format exception should be thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + invalidLineTwo, e.getMessage());
        }

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidLineThree));
            fail("Input Data has invalid format exception should be thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + invalidLineThree, e.getMessage());
        }

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(emptyLine));
            fail("Input Data has invalid format exception should be thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + emptyLine, e.getMessage());
        }
    }

    @Test
    public void stringToFruitTransactionConverter_invalidOperationExceptionMessage_Ok() {
        String invalidOperationLetter = "m,pineapple,10";
        String[] invalidOperationLetterArray = invalidOperationLetter.split(",");
        String invalidOperationNumber = "9,pineapple,10";
        String[] invalidOperationNumberArray = invalidOperationNumber.split(",");
        String invalidOperationSymbol = "~,pineapple,10";
        String[] invalidOperationSymbolArray = invalidOperationSymbol.split(",");

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidOperationLetter));
            fail("Unknown operation, should throw exception");
        } catch (RuntimeException e) {
            assertEquals("Unknown operation: " + invalidOperationLetterArray[0], e.getMessage());
        }

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidOperationNumber));
            fail("Unknown operation, should throw exception");
        } catch (RuntimeException e) {
            assertEquals("Unknown operation: " + invalidOperationNumberArray[0], e.getMessage());
        }

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidOperationSymbol));
            fail("Unknown operation, should throw exception");
        } catch (RuntimeException e) {
            assertEquals("Unknown operation: " + invalidOperationSymbolArray[0], e.getMessage());
        }
    }
}
