package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitRecordDtoParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static FruitRecordDtoParser fruitRecordDtoParser;

    @BeforeClass
    public static void beforeClass() {
        fruitRecordDtoParser = new FruitRecordDtoParserImpl();
    }

    @Test
    public void parse_correctDataFromFile_Ok() {
        List<String> dataFromFile = new ArrayList<>(Arrays.asList("type,fruit,quantity",
                "b,banana,10",
                "s,banana,20",
                "p,banana,15"));
        List<FruitRecordDto> actual = fruitRecordDtoParser.parse(dataFromFile);
        List<FruitRecordDto> expected = List.of(new FruitRecordDto(Operation.BALANCE, "banana", 10),
                new FruitRecordDto(Operation.SUPPLY, "banana", 20),
                new FruitRecordDto(Operation.PURCHASE, "banana", 15));
        assertEquals(expected, actual);
    }
}
