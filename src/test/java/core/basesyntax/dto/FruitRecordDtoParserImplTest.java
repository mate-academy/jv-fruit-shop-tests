package core.basesyntax.dto;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.OperationType;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static final int INDEX_OF_FIRST_ELEMENT = 0;
    private static List<String> testList;
    private final FruitRecordDtoParser parser = new FruitRecordDtoParserImpl();

    @BeforeClass
    public static void beforeClass() {
        testList = new ArrayList<>();
    }

    @After
    public void afterEachTest() {
        testList.clear();
    }

    @Test
    public void parse_balanceOperation_isOk() {
        testList.add("b,apple,100");
        FruitRecordDto expected = new FruitRecordDto(OperationType.BALANCE, "apple", 100);
        FruitRecordDto actual = parser.parse(testList).get(INDEX_OF_FIRST_ELEMENT);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_supplyOperation_isOk() {
        testList.add("s,apple,10");
        FruitRecordDto expected = new FruitRecordDto(OperationType.SUPPLY, "apple", 10);
        FruitRecordDto actual = parser.parse(testList).get(INDEX_OF_FIRST_ELEMENT);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_returnOperation_isOk() {
        testList.add("r,apple,20");
        FruitRecordDto expected = new FruitRecordDto(OperationType.RETURN, "apple", 20);
        FruitRecordDto actual = parser.parse(testList).get(INDEX_OF_FIRST_ELEMENT);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_purchaseOperation_isOk() {
        testList.add("p,apple,30");
        FruitRecordDto expected = new FruitRecordDto(OperationType.PURCHASE, "apple", 30);
        FruitRecordDto actual = parser.parse(testList).get(INDEX_OF_FIRST_ELEMENT);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_infoLine_isOk() {
        testList.add("type,fruit,quantity");
        List<FruitRecordDto> expected = parser.parse(testList);
        assertEquals(new ArrayList<>(), expected);
    }

    @Test
    public void parse_emptyLine_isOk() {
        testList.add("");
        List<FruitRecordDto> expected = parser.parse(testList);
        assertEquals(new ArrayList<>(), expected);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectOperation_notOk() {
        testList.add("h,apple,30");
        FruitRecordDto expected = parser.parse(testList).get(INDEX_OF_FIRST_ELEMENT);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectFruit_notOk() {
        testList.add("b,404error,30");
        FruitRecordDto expected = parser.parse(testList).get(INDEX_OF_FIRST_ELEMENT);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectQuantity_notOk() {
        testList.add("b,banana,-1x0");
        FruitRecordDto expected = parser.parse(testList).get(INDEX_OF_FIRST_ELEMENT);
    }
}
