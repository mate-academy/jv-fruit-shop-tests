package core.basesyntax.service;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;




public class InputDataServiceImplTest {
    @Test
    public void stringToFruitTransactionConverter_validInputTextFormat_ok() {
        InputDataService inputDataService = new InputDataServiceImpl();
        String validLineOne = "b,pineapple,10";
        String validLineTwo = "r,banana,100";
        String validLineThree = "p,orange,50";
        List<String> validInputTextList = List.of(validLineOne, validLineTwo, validLineThree);
        assertEquals(3, inputDataService.stringToFruitTransactionConverter(validInputTextList).size());
    }

    @Test
    public void stringToFruitTransactionConverter_invalidDataFormatExceptionMessage_Ok() {
        InputDataService inputDataService = new InputDataServiceImpl();
        String invalidLineOne = "pineapple,10";
        String invalidLineTwo = "r,100";
        String invalidLineThree = "p,orange";
        String emptyLine = "";

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidLineOne));
            fail("Exception not thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + invalidLineOne, e.getMessage());
        }

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidLineTwo));
            fail("Exception not thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + invalidLineTwo, e.getMessage());
        }

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(invalidLineThree));
            fail("Exception not thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + invalidLineThree, e.getMessage());
        }

        try {
            inputDataService.stringToFruitTransactionConverter(List.of(emptyLine));
            fail("Exception not thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid input data format: " + emptyLine, e.getMessage());
        }
    }
}
