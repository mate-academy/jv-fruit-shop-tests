package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.service.FruitParser;
import core.basesyntax.service.OperationType;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserImplTest {
    private static List<String> inputDataList;
    private static FruitParser parser;
    private static List<FruitRecordDto> expectedList;

    @BeforeClass
    public static void setUp() {
        inputDataList = new ArrayList<>();
        parser = new FruitParserImpl();
        expectedList = new ArrayList<>();
    }

    @Test
    public void testParse_withValidData_isOk() {
        expectedList.add(new FruitRecordDto(OperationType.BALANCE, "banana", 50));
        expectedList.add(new FruitRecordDto(OperationType.PURCHASE, "apple", 100));
        expectedList.add(new FruitRecordDto(OperationType.SUPPLY, "banana", 25));
        expectedList.add(new FruitRecordDto(OperationType.RETURN, "apple", 75));

        inputDataList =
                List.of("type,fruit,quantity", "b,banana,50",
                        "p,apple,100", "s,banana,25", "r,apple,75");
        assertEquals(expectedList, parser.parse(inputDataList));
    }

    @Test
    public void testParse_withOnlyOneLine_isOk() {
        inputDataList.add("type,fruit,quantity");
        assertEquals(new ArrayList<>(), parser.parse(inputDataList));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParse_withInvalidCountOfDataInLine_isNotOk() {
        inputDataList = List.of("type,fruit,quantity,7", "z,banana,50,appendix");
        parser.parse(inputDataList);
    }

    @Test(expected = RuntimeException.class)
    public void testParse_withInvalidOperationType_isNotOk() {
        inputDataList = List.of("type,fruit,quantity", "z,banana,50");
        parser.parse(inputDataList);
    }

    @Test(expected = RuntimeException.class)
    public void testParse_withInvalidQuantityType_isNotOk() {
        inputDataList = List.of("type,fruit,quantity,7", "z,banana,1OO");
        parser.parse(inputDataList);
    }

    @Test(expected = RuntimeException.class)
    public void testParse_withNegativeQuantityType_isNotOk() {
        inputDataList = List.of("type,fruit,quantity,7", "p,banana,1OO");
        parser.parse(inputDataList);
    }
}
