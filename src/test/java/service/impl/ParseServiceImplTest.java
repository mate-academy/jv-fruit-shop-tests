package service.impl;

import java.util.ArrayList;
import java.util.List;
import model.Fruit;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ParseService;

public class ParseServiceImplTest {
    private static List<String> lineList;
    private static List<FruitTransaction> expected;
    private static List<FruitTransaction> actual;
    private static ParseService fruitParser;

    @BeforeClass
    public static void beforeClass() {
        actual = new ArrayList<>();
        fruitParser = new ParseServiceImpl();
        lineList = new ArrayList<>();
        lineList.add("type,fruit,quantity");
        lineList.add("b,banana,100");
        lineList.add("r,apple,200");
        lineList.add("s,banana,4");
        expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 100));
        expected.add(new FruitTransaction("r", new Fruit("apple"), 200));
        expected.add(new FruitTransaction("s", new Fruit("banana"), 4));
    }

    @Test
    public void parseValidData_isOk() {
        actual = fruitParser.parse(lineList);
        Assert.assertEquals(expected, actual);
    }
}
