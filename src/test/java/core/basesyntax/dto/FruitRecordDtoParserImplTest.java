package core.basesyntax.dto;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static final FruitRecordDtoParser fruitRecordDtoParser = new FruitRecordDtoParserImpl();

    @Test(expected = RuntimeException.class)
    public void parse_illegalCharactersInput_Not_Ok() {
        List<String> lines = List.of("one, two, three",
                "four, five, six",
                "seven, eleven, twelve");
        fruitRecordDtoParser.parse(lines);
        assertEquals(new RuntimeException("Illegal characters in use..."),
                fruitRecordDtoParser.parse(lines));

    }

    @Test (expected = RuntimeException.class)
    public void parse_incorrectQuantityNotNumber_notOk() {
        List<String> lines = Arrays.asList("type,fruit,quantity",
                "b,apple,two");
        fruitRecordDtoParser.parse(lines);
    }
}
