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
    public void parseLines_ValidList_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("s,apple,100");
        List<FruitTransaction> expectedList = new ArrayList<>();
        expectedList.add(new FruitTransaction("b", new Fruit("banana"), 20));
        expectedList.add(new FruitTransaction("s", new Fruit("apple"), 100));
        List<FruitTransaction> actualList = parserService.parseLines(lines);
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(0).getFruit(), actualList.get(0).getFruit());
            assertEquals(expectedList.get(0).getQuantity(), actualList.get(0).getQuantity());
            assertEquals(expectedList.get(0).getOperation(), actualList.get(0).getOperation());
        }
    }

    @Test(expected = NullPointerException.class)
    public void parseLines_NullList_NotOk() {
        List<String> lines = null;
        parserService.parseLines(lines);
    }
}
