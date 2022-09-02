package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private ParserService parserService;

    @Before
    public void setUp() throws Exception {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseInfo_Ok() {
        List<String> test = new ArrayList<>();
        test.add("type,fruit,quantity");
        test.add("b,banana,100");
        test.add("r,banana,5");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 100));
        expected.add(new FruitTransaction("r", new Fruit("banana"), 5));
        List<FruitTransaction> actual = parserService.parse(test);
        assertEquals(expected, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void expected_Exception_NotOk() {
        List<String> test = new ArrayList<>();
        test.add("b,apple,number");
        test.add("s,banana,  ");
        parserService.parse(test);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parse_EmptyList_NotOk() {
        List<String> test = new ArrayList<>();
        parserService.parse(test);
    }

    @Test(expected = NullPointerException.class)
    public void parse_ListWithNull_NotOk() {
        List<String> test = new ArrayList<>();
        test.add("type,fruit,quantity");
        test.add(null);
        parserService.parse(test);

    }
}
