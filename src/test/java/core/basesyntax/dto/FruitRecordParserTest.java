package core.basesyntax.dto;

import static org.junit.Assert.assertEquals;

import core.basesyntax.models.Fruit;
import core.basesyntax.models.FruitRecord;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class FruitRecordParserTest {
    private static final ParseService parser = new FruitRecordParser();
    private static final String validData = new StringBuilder()
            .append("type,fruit,quantity")
            .append(System.lineSeparator())
            .append("b,banana,20")
            .append(System.lineSeparator())
            .append("b,apple,100")
            .append(System.lineSeparator())
            .toString();
    private static final FruitRecord bananaRecord
            = new FruitRecord('b', "banana", 20);
    private static final FruitRecord appleRecord
            = new FruitRecord('b', "apple", 100);
    private static final Fruit banana
            = new Fruit("banana", 20);
    private static final Fruit apple
            = new Fruit("apple", 100);

    @Test(expected = RuntimeException.class)
    public void parseFromCsv_nullString_Exception() {
        parser.parseFromCsv(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseFromCsv_emptyString_Exception() {
        parser.parseFromCsv("");
    }

    @Test(expected = RuntimeException.class)
    public void parseFromCsv_noTitle_Exception() {
        parser.parseFromCsv(new StringBuilder().append("b,banana,20")
                .append(System.lineSeparator())
                .append("b,apple,100")
                .append(System.lineSeparator())
                .toString());
    }

    @Test
    public void parseFromCsv_Ok() {
        List<FruitRecord> actualList = parser.parseFromCsv(validData);
        boolean expected = true;
        boolean actual = actualList.contains(bananaRecord)
                && actualList.contains(appleRecord);
        assertEquals(expected, actual);
    }

    @Test
    public void parseIntoCsv_Ok() {
        boolean expected = true;
        String expectedString = new StringBuilder()
                .append("banana,20")
                .append(System.lineSeparator())
                .append("apple,100")
                .toString();
        Set<Fruit> fruits = new HashSet<>();
        fruits.add(apple);
        fruits.add(banana);
        String actualString = parser.parseIntoCsv(fruits);
        boolean actual = actualString.equals(expectedString);
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseIntoCsv_emptySet_Exception() {
        String actualString = parser.parseIntoCsv(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseIntoCsv_setWithNull_Exception() {
        Set<Fruit> fruitSet = new HashSet<>();
        fruitSet.add(null);
        String actualString = parser.parseIntoCsv(null);
    }
}
