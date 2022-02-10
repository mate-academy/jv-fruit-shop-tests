package core.basesyntax.services.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TypeOfOperation;
import core.basesyntax.services.FruitDtoParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitDtoParserImplTest {
    private static final String TITLE = "type,fruit,quantity";
    private static List<String> dataFromFile;
    private static FruitDtoParser fruitDtoParser;

    @Before
    public void setUp() {
        dataFromFile = new ArrayList<>();
        dataFromFile.add(TITLE);
        dataFromFile.add("b,banana,20");
        dataFromFile.add("b,apple,100");
        fruitDtoParser = new FruitDtoParserImpl();
    }

    @Test
    public void parsingData_ok() {
        FruitDto actualFruitDto = fruitDtoParser.parseFruitDto(dataFromFile).get(1);
        FruitDto expectedFruitDto = new FruitDto(TypeOfOperation.BALANCE, new Fruit("apple"), 100);
        assertEquals(actualFruitDto.toString(), expectedFruitDto.toString());
    }

    @Test
    public void parsingData_checkDataLength_ok() {
        int actual = fruitDtoParser.parseFruitDto(dataFromFile).size();
        int expected = 2;
        assertEquals(expected, actual);
    }
}
