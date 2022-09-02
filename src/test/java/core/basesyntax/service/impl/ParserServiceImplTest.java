package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseLines_ListIsValid_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("s,apple,100");
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction("b", new Fruit("banana"), 20));
        expectedList.add(new FruitTransaction("s", new Fruit("apple"), 100));
        List<FruitTransaction> actualList = parserService.parseLines(lines);
        assertEquals(expectedList, actualList);
    }

    @Test(expected = NullPointerException.class)
    public void parseLines_ListIsNull_NotOk() {
        List<String> lines = null;
        parserService.parseLines(lines);
    }
}
