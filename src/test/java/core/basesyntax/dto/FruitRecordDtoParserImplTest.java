package core.basesyntax.dto;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static FruitRecordDtoParser fruitRecordDtoParser;

    @BeforeClass
    public static void initializeObject() {
        fruitRecordDtoParser = new FruitRecordDtoParserImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parse_illegalCharactersInput_Not_Ok() {
        List<String> lines = List.of("one, two, three",
                "four, five, six",
                "seven, eleven, twelve");
        fruitRecordDtoParser.parse(lines);
    }

    @Test (expected = RuntimeException.class)
    public void parse_incorrectQuantityNotNumber_notOk() {
        List<String> lines = Arrays.asList("type,fruit,quantity", "b,apple,two");
        fruitRecordDtoParser.parse(lines);
    }

    @Test
    public void parse_correctInput_Ok() {
        List<String> lines = Arrays.asList("type, fruit, quantity", "b, apple, 2");
        FruitRecordDto expected = new FruitRecordDto(Operation.BALANCE,"apple", 2);
        FruitRecordDto fruitRecordDto = fruitRecordDtoParser.parse(lines).get(0);
        assertEquals(expected, fruitRecordDto);
    }
}
