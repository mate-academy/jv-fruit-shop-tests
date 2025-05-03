package core.basesyntax.service.parser;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitDto;
import core.basesyntax.operations.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserDtoTest {
    private static final Operation OPERATION = Operation.SUPPLY;
    private static final String FRUIT_NAME = "banana";
    private static final Integer QUANTITY = 15;
    private static final String TEST_STRING = "s,banana,15";
    private static final String TEST_WRONG_STRING = "m,banana,15";
    private static final String TITLE = "type,fruit,quantity";
    private static Parser parser = new FruitParserDto();
    private static List<FruitDto> fruitDtoList;
    private static List<String> stringList;

    @BeforeClass
    public static void beforeClass() {
        fruitDtoList = new ArrayList<>();
        stringList = new ArrayList<>();
    }

    @Test
    public void parseInformation_Ok() {
        fruitDtoList.add(new FruitDto(OPERATION, FRUIT_NAME, QUANTITY));
        stringList.add(TITLE);
        stringList.add(TEST_STRING);
        List<FruitDto> expected = fruitDtoList;
        List<FruitDto> actual = parser.parseInformation(stringList);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void parseInformation_NotOk() {
        stringList.add(TITLE);
        stringList.add(TEST_WRONG_STRING);
        parser.parseInformation(stringList);
    }
}
