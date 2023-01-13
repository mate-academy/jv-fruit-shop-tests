package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static List<String> validData;
    private static List<FruitTransaction> expected;
    private static DataParserServiceImpl dataParserService;

    @BeforeClass
    public static void beforeClass() {
        dataParserService = new DataParserServiceImpl();
    }

    @Before
    public void setUp() {
        validData = new ArrayList<>();
        expected = new ArrayList<>();
    }

    @Test
    public void parseData_listWithValidDataType_ok() {
        validData.add("b,banana,20");
        validData.add("s,banana,100");
        validData.add("p,banana,13");
        validData.add("r,apple,10");
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        List<FruitTransaction> actual = dataParserService.parseData(validData);
        assertEquals(String.format("Expected list -> %s%nwfor input -> %s%nbut was -> %s",
                expected, validData, actual), expected, actual);
    }

    @Test
    public void parseData_operationLetterInUpperCase_Ok() {
        validData.add("B,banana,20");
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        List<FruitTransaction> actual = dataParserService.parseData(validData);
        assertEquals(String.format("When input operation name in upper case, "
                        + "expected list is-> %s%nfor input -> %s%nbut was -> %s",
                expected, validData, actual), expected, actual);
    }

    @Test
    public void parseData_quantityIsNegative_Ok() {
        validData.add("B,banana,-20");
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        List<FruitTransaction> actual = dataParserService.parseData(validData);
        assertEquals(String.format("Expected list -> %s%n"
                        + "when input data with negative quantity -> %s%nbut was -> %s",
                expected, validData, actual), expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void parseData_operationLetterIsInvalid_Ok() {
        validData.add("!,oops,0");
        dataParserService.parseData(validData);
    }

    @Test
    public void parseData_inputListIsEmpty_Ok() {
        List<FruitTransaction> actual = dataParserService.parseData(validData);
        assertEquals(String.format("When input list is empty, "
                + "should return empty list, but was -> %s", actual), expected, actual);
    }

    @Test
    public void parseData_inputListIsNull_Ok() {
        validData.add(null);
        List<FruitTransaction> actual = dataParserService.parseData(validData);
        assertEquals(String.format("When input list contains null, "
                + "should return empty list, but was -> %s", actual), expected, actual);
    }
}
