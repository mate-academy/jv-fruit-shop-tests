package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.dto.FruitRecordDtoParser;
import core.basesyntax.dto.FruitRecordDtoParserImpl;
import core.basesyntax.model.OperationType;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static FruitRecordDtoParser fruitRecordDtoParser;

    @BeforeClass
    public static void setFruitRecordDtoParser() {
        fruitRecordDtoParser = new FruitRecordDtoParserImpl();
    }

    @Test
    public void parse_ValidData_isOk() {
        List<String> data = List.of("type,fruit,quantity", "b,apple,100",
                "b,orange,200", "s,banana,50");
        List<FruitRecordDto> result = fruitRecordDtoParser.parse(data);
        List<FruitRecordDto> expected = List.of(
                new FruitRecordDto(OperationType.BALANCE, "apple", 100),
                new FruitRecordDto(OperationType.BALANCE, "orange", 200),
                new FruitRecordDto(OperationType.SUPPLY, "banana", 50));
        assertEquals(expected, result);
    }

    @Test(expected = RuntimeException.class)
    public void parse_InvalidOperation_notOk() {
        List<String> data = List.of("type,fruit,quantity", "r,apple,100",
                "t,orange,200", "s,banana,50");
        fruitRecordDtoParser.parse(data);
    }

    @Test(expected = RuntimeException.class)
    public void parse_InvalidData_notOk() {
        List<String> data = List.of("type,fruit,quantity", "apple",
                "orange", "banana,50");
        fruitRecordDtoParser.parse(data);
    }

    @Test(expected = RuntimeException.class)
    public void parse_InvalidAmount_notOk() {
        List<String> data = List.of("type,fruit,quantity", "r,apple,-100",
                "b,orange,200", "s,banana,50");
        fruitRecordDtoParser.parse(data);
    }
}
