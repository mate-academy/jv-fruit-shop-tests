package core.basesyntax.service.impl;

import core.basesyntax.dto.FruitRecordDto;
import core.basesyntax.service.FruitRecordDtoParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private final FruitRecordDtoParser fruitRecordDtoParser = new FruitRecordDtoParserImpl();
    private List<FruitRecordDto> fruitRecordDtoList;

    @Before
    public void beforeClass() {
        fruitRecordDtoList = new ArrayList<>();
    }

    @Test
    public void parse_correctParselines_Ok() {
        List<String> list = List.of("b,banana,20");
        fruitRecordDtoList.add(new FruitRecordDto("b", "banana", 20));
        List<FruitRecordDto> expected = fruitRecordDtoList;
        List<FruitRecordDto> actual = fruitRecordDtoParser.parse(list);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_NotCorrectLength_() {
        List<String> list = List.of("b, banana");
        fruitRecordDtoParser.parse(list);
    }

    @Test(expected = RuntimeException.class)
    public void parse_NotCorrectQuantity() {
        List<String> list = List.of("b,banana,20d");
        fruitRecordDtoParser.parse(list);
    }

    @Test(expected = RuntimeException.class)
    public void parse_NotCorrectSplitLine() {
        List<String> list = List.of("b;banana;20");
        fruitRecordDtoParser.parse(list);
    }
}
