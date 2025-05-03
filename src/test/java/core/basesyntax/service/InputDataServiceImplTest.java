package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

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

    @Test(expected = RuntimeException.class)
    public void stringToFruitTransactionConverter_invalidDataFormatExceptionMessage_ok() {
        String invalidLineOne = "pineapple,10";
        inputDataService.stringToFruitTransactionConverter(List.of(invalidLineOne));
    }

    @Test(expected = RuntimeException.class)
    public void stringToFruitTransactionConverter_inputEmptyLineExceptionMessage_ok() {
        String emptyLine = "";
        inputDataService.stringToFruitTransactionConverter(List.of(emptyLine));
    }

    @Test(expected = RuntimeException.class)
    public void stringToFruitTransactionConverter_inputNullLineExceptionMessage_ok() {
        String nullLine = new String();
        inputDataService.stringToFruitTransactionConverter(List.of(nullLine));
    }

    @Test(expected = RuntimeException.class)
    public void stringToFruitTransactionConverter_invalidLetterOperationExceptionMessage_ok() {
        String invalidOperationLetter = "m,pineapple,10";
        String[] invalidOperationLetterArray = invalidOperationLetter.split(",");
        inputDataService.stringToFruitTransactionConverter(List.of(invalidOperationLetter));
    }

    @Test(expected = RuntimeException.class)
    public void stringToFruitTransactionConverter_invalidNumberOperationExceptionMessage_ok() {
        String invalidOperationNumber = "9,pineapple,10";
        String[] invalidOperationNumberArray = invalidOperationNumber.split(",");
        inputDataService.stringToFruitTransactionConverter(List.of(invalidOperationNumber));
    }
}
