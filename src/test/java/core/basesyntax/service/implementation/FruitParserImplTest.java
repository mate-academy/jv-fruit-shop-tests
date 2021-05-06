package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.service.FruitParser;
import core.basesyntax.service.OperationType;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FruitParserImplTest {
    private static List<String> INPUT_DATA_LIST = new ArrayList<>();
    private static final FruitParser PARSER = new FruitParserImpl();
    private static final List<FruitRecordDto> EXPECTED_LIST = new ArrayList<>();

    @Test
    public void testParse_withValidData_isOk() {
        EXPECTED_LIST.add(new FruitRecordDto(OperationType.BALANCE, "banana", 50));
        EXPECTED_LIST.add(new FruitRecordDto(OperationType.PURCHASE, "apple", 100));
        EXPECTED_LIST.add(new FruitRecordDto(OperationType.SUPPLY, "banana", 25));
        EXPECTED_LIST.add(new FruitRecordDto(OperationType.RETURN, "apple", 75));

        INPUT_DATA_LIST =
                List.of("type,fruit,quantity", "b,banana,50",
                        "p,apple,100", "s,banana,25", "r,apple,75");
        assertEquals(EXPECTED_LIST, PARSER.parse(INPUT_DATA_LIST));
    }

    @Test
    public void testParse_withOnlyOneLine_isOk() {
        INPUT_DATA_LIST.add("type,fruit,quantity");
        assertEquals(new ArrayList<>(), PARSER.parse(INPUT_DATA_LIST));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParse_withInvalidCountOfDataInLine_isNotOk() {
        INPUT_DATA_LIST = List.of("type,fruit,quantity,7", "z,banana,50,appendix");
        PARSER.parse(INPUT_DATA_LIST);
    }

    @Test(expected = RuntimeException.class)
    public void testParse_withInvalidOperationType_isNotOk() {
        INPUT_DATA_LIST = List.of("type,fruit,quantity", "z,banana,50");
        PARSER.parse(INPUT_DATA_LIST);
    }

    @Test(expected = RuntimeException.class)
    public void testParse_withInvalidQuantityType_isNotOk() {
        INPUT_DATA_LIST = List.of("type,fruit,quantity,7", "z,banana,1OO");
        PARSER.parse(INPUT_DATA_LIST);
    }

    @Test(expected = RuntimeException.class)
    public void testParse_withNegativeQuantityType_isNotOk() {
        INPUT_DATA_LIST = List.of("type,fruit,quantity,7", "p,banana,1OO");
        PARSER.parse(INPUT_DATA_LIST);
    }
}
