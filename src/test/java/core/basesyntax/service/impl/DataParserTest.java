package core.basesyntax.service.impl;

import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserTest {
    private static List<String> fruitList;
    private static Parser dataParser;

    @BeforeClass
    public static void init() {
        fruitList = new ArrayList<>();
        dataParser = new DataParser();
    }

    @Test
    public void parseValidDataFromInput_Ok() {
        fruitList.add("apple,20");
        fruitList.add("banana,45");
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,20" + System.lineSeparator()
                + "banana,45";
        String actual = dataParser.formatReport(fruitList);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseValidDataFromStorage_Ok() {
        fruitList.add("type,fruit,quantity");
        fruitList.add("b,apple,30");
        fruitList.add("b,banana,45");
        fruitList.add("r,apple,2");
        fruitList.add("s,banana,56");
        List<String[]> expected = List.of(new String[]{"b", "apple", "30"},
                new String[]{"b", "banana", "45"},
                new String[]{"r", "apple", "2"},
                new String[]{"s", "banana", "56"});
        List<String[]> actual = dataParser.formatInputData(fruitList);
        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertArrayEquals(expected.get(i), actual.get(i));
        }
    }

    @After
    public void resetList() {
        fruitList.clear();
    }
}
