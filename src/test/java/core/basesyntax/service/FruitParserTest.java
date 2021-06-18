package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exeptions.ValidationException;
import core.basesyntax.model.FruitDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FruitParserTest {
    private final FruitParser fruitParser = new FruitParser();

    @Test
    public void parsingStringsToObject_Ok() throws ValidationException {
        List<String> databaseInfo = new ArrayList<>();
        databaseInfo.add("type,fruit,quantity");
        databaseInfo.add("b,banana,20");
        databaseInfo.add("b,apple,100");
        databaseInfo.add("s,banana,100");
        databaseInfo.add("p,banana,13");
        databaseInfo.add("r,apple,10");
        databaseInfo.add("p,apple,20");
        databaseInfo.add("p,banana,5");
        databaseInfo.add("s,banana,50");
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("b", "banana", 20));
        expected.add(new FruitDto("b", "apple", 100));
        expected.add(new FruitDto("s", "banana", 100));
        expected.add(new FruitDto("p", "banana", 13));
        expected.add(new FruitDto("r", "apple", 10));
        expected.add(new FruitDto("p", "apple", 20));
        expected.add(new FruitDto("p", "banana", 5));
        expected.add(new FruitDto("s", "banana", 50));

        List<FruitDto> actual = fruitParser.parserFruit(databaseInfo);
        assertEquals(expected, actual);
    }

    @Test
    public void datafileHasMistakes_Ok() {
        List<String> databaseInfo = new ArrayList<>();
        databaseInfo.add("type,fruit,quantity");
        databaseInfo.add("b,banana,20");
        databaseInfo.add("b");
        databaseInfo.add("s,banana,100");
        Assertions.assertThrows(ValidationException.class, () -> {
            fruitParser.parserFruit(databaseInfo);
        });
    }

    @Test
    public void parsingStringsWithoutHeader_Ok() throws ValidationException {
        List<String> databaseInfo = new ArrayList<>();
        databaseInfo.add("b,banana,20");
        databaseInfo.add("b,apple,100");
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("b", "banana", 20));
        expected.add(new FruitDto("b", "apple", 100));
        List<FruitDto> actual = fruitParser.parserFruit(databaseInfo);
        assertEquals(expected, actual);
    }

    @Test
    public void datafileHasMistakesWithoutCountFruit_Ok() {
        List<String> databaseInfo = new ArrayList<>();
        databaseInfo.add("b,banana");
        databaseInfo.add("b,banana");
        databaseInfo.add("s,banana");
        Assertions.assertThrows(ValidationException.class, () -> {
            fruitParser.parserFruit(databaseInfo);
        });
    }
}
