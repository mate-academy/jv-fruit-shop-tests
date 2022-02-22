package services;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Fruit;
import model.FruitRecord;
import org.junit.Test;
import services.impl.ParseServiceImpl;

public class ParseServiceTest {
    private static final ParseService parser = new ParseServiceImpl();
    private static final String validData = "type,fruit,quantity" +
            System.lineSeparator() +
            "b,banana,20" +
            System.lineSeparator() +
            "b,apple,100" +
            System.lineSeparator();
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

    @Test
    public void parseFromCsv_Ok() {
        List<FruitRecord> actualList = parser.parseFromCsv(validData);
        boolean expected = true;
        boolean actual = actualList.contains(bananaRecord)
                && actualList.contains(appleRecord);
        assertEquals(expected, actual);
    }

    @Test
    public void parseToString_Ok() {
        boolean expected = true;
        String expectedString = new StringBuilder()
                .append("banana,20")
                .append(System.lineSeparator())
                .append("apple,100")
                .toString();
        Set<Fruit> fruits = new HashSet<>();
        fruits.add(apple);
        fruits.add(banana);
        String actualString = parser.parseToString(fruits);
        boolean actual = actualString.equals(expectedString);
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseToString_emptySet_Exception() {
        String actualString = parser.parseToString(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseToString_setWithNull_Exception() {
        Set<Fruit> fruitSet = new HashSet<>();
        fruitSet.add(null);
        String actualString = parser.parseToString(null);
    }
}
