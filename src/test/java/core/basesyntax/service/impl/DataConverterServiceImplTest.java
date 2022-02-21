package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.dto.FruitDto;
import core.basesyntax.service.DataConverterService;
import core.basesyntax.service.DataReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DataConverterServiceImplTest {
    private static final String TEST_FEW_LINES = "src/test/resources/testInput.csv";
    private static final String BAD_INPUT_FILE = "src/test/resources/IncorrectTest.csv";
    private DataConverterService dataConverterService;
    private DataReaderService dataReaderService;

    @Before
    public void init() {
        dataReaderService = new DataReaderServiceImpl();
        dataConverterService = new DataConverterServiceImpl();
    }

    @Test
    public void convertDto_positiveTest_ok() {
        List<FruitDto> actual = dataConverterService
                .convertDto(dataReaderService.readDataFromFile(TEST_FEW_LINES));
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("b", "banana", 20));
        expected.add(new FruitDto("b", "apple", 100));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void convertDto_badFormatContent_notOk() {
        List<FruitDto> actual = dataConverterService
                .convertDto(dataReaderService
                        .readDataFromFile(BAD_INPUT_FILE));
    }
}
