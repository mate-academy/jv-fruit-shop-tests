package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.service.FruitParser;
import core.basesyntax.service.OperationType;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FruitParserImplTest {
    private static List<String> inputDataList = new ArrayList<>();
    private static FruitParser fruitParser = new FruitParserImpl();
    private static List<FruitRecordDto> expectedList = new ArrayList<>();

    @Test
    public void testParse_withValidData_isOk() {
        expectedList.add(new FruitRecordDto(OperationType.BALANCE, "banana", 50));
        expectedList.add(new FruitRecordDto(OperationType.PURCHASE, "apple", 100));
        expectedList.add(new FruitRecordDto(OperationType.SUPPLY, "banana", 25));
        expectedList.add(new FruitRecordDto(OperationType.RETURN, "apple", 75));

        inputDataList =
                List.of("type,fruit,quantity", "b,banana,50",
                        "p,apple,100", "s,banana,25", "r,apple,75");
        List<FruitRecordDto> actualList = fruitParser.parse(inputDataList);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testParse_withOnlyOneLine_isOk() {
        inputDataList.add("type,fruit,quantity");
        List<FruitRecordDto> expected = fruitParser.parse(inputDataList);
        assertEquals(new ArrayList<>(), expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParse_withInvalidCountOfDataInLine_isNotOk() {
        inputDataList = List.of("type,fruit,quantity,7", "z,banana,50,appendix");
        fruitParser.parse(inputDataList);
    }

    @Test(expected = RuntimeException.class)
    public void testParse_withInvalidOperationType_isNotOk() {
        inputDataList = List.of("type,fruit,quantity", "z,banana,50");;
        fruitParser.parse(inputDataList);
    }

    @Test(expected = RuntimeException.class)
    public void testParse_withInvalidQuantityType_isNotOk() {
        inputDataList = List.of("type,fruit,quantity,7", "z,banana,1OO");
        fruitParser.parse(inputDataList);
    }

    @Test(expected = RuntimeException.class)
    public void testParse_withNegativeQuantityType_isNotOk() {
        inputDataList = List.of("type,fruit,quantity,7", "z,banana,1OO");
        fruitParser.parse(inputDataList);
    }
}
