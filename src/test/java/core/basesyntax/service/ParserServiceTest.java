package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceTest {
    private ParserService parserService;
    private List<String> testListLines;
    private List<Transaction> expected;
    private List<Transaction> actual;

    @Before
    public void setUp() {
        actual = new ArrayList<>();
        expected = new ArrayList<>();
        testListLines = new ArrayList<>();
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseEmptyLine_ok() {
        testListLines.add("");
        actual = parserService.parse(testListLines);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_ok() {
        testListLines.add("type,fruit,quantity");
        testListLines.add("b,banana,20");
        testListLines.add("b,apple,100");
        expected.add(new Transaction("b", new Fruit("banana"), 20));
        expected.add(new Transaction("b", new Fruit("apple"), 100));
        actual = parserService.parse(testListLines);
        assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parseWithoutFruit_notOk() {
        testListLines.add("type,fruit,quantity");
        testListLines.add("b,20");
        testListLines.add("b,apple,100");
        parserService.parse(testListLines);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parseWithoutCount_notOk() {
        testListLines.add("type,fruit,quantity");
        testListLines.add("banana,20");
        testListLines.add("b,apple,100");
        parserService.parse(testListLines);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void parseWithoutParameters_notOk() {
        testListLines.add("type,fruit,quantity");
        testListLines.add("b,banana");
        testListLines.add("b,apple,100");
        parserService.parse(testListLines);
    }
}
