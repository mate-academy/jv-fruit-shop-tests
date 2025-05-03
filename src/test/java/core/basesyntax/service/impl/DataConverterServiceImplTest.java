package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.dto.FruitDto;
import core.basesyntax.service.DataConverterService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DataConverterServiceImplTest {
    private DataConverterService dataConverterService;

    @Before
    public void init() {
        dataConverterService = new DataConverterServiceImpl();
    }

    @Test
    public void convertDto_positiveTest_ok() {
        List<String> testStringData = new ArrayList<>();
        testStringData.add("type,fruit,quantity");
        testStringData.add("b,banana,20");
        testStringData.add("b,apple,100");
        List<FruitDto> actual = dataConverterService
                .convertDto(testStringData);
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("b", "banana", 20));
        expected.add(new FruitDto("b", "apple", 100));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void convertDto_badFormatContent_notOk() {
        List<String> testStringData = new ArrayList<>();
        testStringData.add("type,fruit,quantity,redundant");
        testStringData.add("b,banana,20,50");
        testStringData.add("b,apple");
        dataConverterService.convertDto(testStringData);
    }
}
