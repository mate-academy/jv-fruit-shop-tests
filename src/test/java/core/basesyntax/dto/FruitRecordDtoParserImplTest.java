package core.basesyntax.dto;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static FruitRecordDtoParser fruitRecordDtoParser;

    @Before
    public void setUp() throws Exception {
        fruitRecordDtoParser = new FruitRecordDtoParserImpl();
    }

    @Test
    public void parse_correctLinesFromFile_Ok() {
        List<String> lines = new ArrayList(Arrays.asList("type,fruit,quantity",
                "b,banana,40",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"));
        List<FruitRecordDto> actual = fruitRecordDtoParser.parse(lines);
        List<FruitRecordDto> expected = List.of(new FruitRecordDto(Operation.BALANCE, "banana", 40),
                new FruitRecordDto(Operation.BALANCE, "apple", 100),
                new FruitRecordDto(Operation.SUPPLY, "banana", 100),
                new FruitRecordDto(Operation.PURCHASE, "banana", 13),
                new FruitRecordDto(Operation.RETURN, "apple", 10),
                new FruitRecordDto(Operation.PURCHASE, "apple", 20),
                new FruitRecordDto(Operation.PURCHASE, "banana", 5),
                new FruitRecordDto(Operation.SUPPLY, "banana", 50));
        assertEquals(expected.toString(), actual.toString());
    }

    @Test (expected = RuntimeException.class)
    public void parse_incorrectLengthColumns_Ok() {
        List<String> lines = new ArrayList(Arrays.asList("type,fruit,quantity",
                "b,banana,40,50"));
        fruitRecordDtoParser.parse(lines);
    }

    @Test (expected = RuntimeException.class)
    public void parse_incorrectQuantityNotNumber_OK() {
        List<String> lines = new ArrayList(Arrays.asList("type,fruit,quantity",
                "b,apple,two"));
        fruitRecordDtoParser.parse(lines);
    }
}
