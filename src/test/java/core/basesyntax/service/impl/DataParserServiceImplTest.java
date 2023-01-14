package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.WrongOperationLetterException;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static DataParserServiceImpl dataParserService;

    @BeforeClass
    public static void beforeClass() {
        dataParserService = new DataParserServiceImpl();
    }

    @Test
    public void parseData_listWithValidDataType_ok() {
        List<String> testData = new ArrayList<>(
                List.of("b,banana,20", "s,banana,100", "p,banana,13", "r,apple,10"));
        List<FruitTransaction> expected = new ArrayList<>(
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                        new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)));
        List<FruitTransaction> actual = dataParserService.parseData(testData);
        assertEquals(String.format("Expected list -> %s%nwfor input -> %s%nbut was -> %s",
                expected, testData, actual), expected, actual);
    }

    @Test
    public void parseData_operationLetterInUpperCase_ok() {
        List<String> testData = new ArrayList<>(List.of("B,banana,20"));
        List<FruitTransaction> expected = new ArrayList<>(
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20)));
        List<FruitTransaction> actual = dataParserService.parseData(testData);
        assertEquals(String.format("When input operation name in upper case, "
                        + "expected list is-> %s%nfor input -> %s%nbut was -> %s",
                expected, testData, actual), expected, actual);
    }

    @Test
    public void parseData_quantityIsNegative_ok() {
        List<String> testData = new ArrayList<>(List.of("B,banana,-20"));
        List<FruitTransaction> expected = new ArrayList<>(
                List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20)));
        List<FruitTransaction> actual = dataParserService.parseData(testData);
        assertEquals(String.format("Expected list -> %s%n"
                        + "when input data with negative quantity -> %s%nbut was -> %s",
                expected, testData, actual), expected, actual);
    }

    @Test
    public void parseData_inputListIsEmpty_ok() {
        List<String> testData = new ArrayList<>();
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = dataParserService.parseData(testData);
        assertEquals(String.format("When input list is empty, "
                + "should return empty list, but was -> %s", actual), expected, actual);
    }

    @Test
    public void parseData_inputListIsNull_ok() {
        List<String> testData = new ArrayList<>();
        List<FruitTransaction> expected = new ArrayList<>();
        testData.add(null);
        List<FruitTransaction> actual = dataParserService.parseData(testData);
        assertEquals(String.format("When input list contains null, "
                + "should return empty list, but was -> %s", actual), expected, actual);
    }

    @Test (expected = WrongOperationLetterException.class)
    public void parseData_operationLetterIsInvalid_notOk() {
        List<String> testData = new ArrayList<>(List.of("!,oops,0"));
        dataParserService.parseData(testData);
    }
}
