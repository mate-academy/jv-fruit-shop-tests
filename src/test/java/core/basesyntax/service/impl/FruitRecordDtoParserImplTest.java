package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.service.FruitRecordDtoParser;
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
    public void parse_validInputData_isOk() {
        List<String> inputData = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100");
        List<FruitRecordDto> result = fruitRecordDtoParser.parse(inputData);
        List<FruitRecordDto> expected = List.of(
                new FruitRecordDto(FruitRecordDto.Type.b, "banana", 20),
                new FruitRecordDto(FruitRecordDto.Type.b, "apple", 100),
                new FruitRecordDto(FruitRecordDto.Type.s, "banana", 100));
        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_invalidInputDataLength_isIllegalArgumentException() {
        List<String> inputData = List.of("type,fruit,quantity", "banana,20",
                "b,apple,100", "s,banana,100");
        fruitRecordDtoParser.parse(inputData);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_invalidOperationType_isIllegalArgumentException() {
        List<String> inputData = List.of("type,fruit,quantity", "b,banana,20",
                "g,apple,100", "s,banana,100");
        fruitRecordDtoParser.parse(inputData);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_amountLessThenZero_isIllegalArgumentException() {
        List<String> inputData = List.of("type,fruit,quantity", "b,banana,20",
                "g,apple,100", "s,banana,-100");
        fruitRecordDtoParser.parse(inputData);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_notNumberIsPassedInsteadOfAmount_isIllegalArgumentException() {
        List<String> inputData = List.of("type,fruit,quantity", "b,banana,20",
                "g,apple,hello", "s,banana,100");
        fruitRecordDtoParser.parse(inputData);
    }
}
