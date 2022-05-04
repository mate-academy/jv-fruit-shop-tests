package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.LineInformation;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser fruitParser;
    private static List<String> dataList;
    private static List<LineInformation> expected;

    @BeforeClass
    public static void setUp() {
        fruitParser = new ParserImpl();
        dataList = new ArrayList<>();
        dataList.add("type,fruit,quantity");
        dataList.add("r,apple,15");
        expected = new ArrayList<>();
        expected.add(new LineInformation("r", new Fruit("apple"), 15));
    }

    @Test
    public void parse_Ok() {
        List<LineInformation> actual = fruitParser.parse(dataList);
        Assert.assertEquals(expected, actual);
    }
}
