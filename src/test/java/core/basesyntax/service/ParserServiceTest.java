package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceTest {
    private ParserService parserServiceTest;
    private List<String> testListLines;
    private List<Transaction> expected;
    private List<Transaction> actual;

    @Before
    public void setUp() {
        actual = new ArrayList<>();
        expected = new ArrayList<>();
        testListLines = new ArrayList<>();
        parserServiceTest = new ParserServiceImpl();
    }

    @Test
    public void parseEmptyLine_notOk() {
        testListLines.add("");
        actual = parserServiceTest.parse(testListLines);
        assertEquals(expected, actual);
    }

    @Test
    public void parseNull_notOk() {
        testListLines.add(null);
        parserServiceTest.parse(testListLines);
        assertNull(testListLines.get(0));
    }

    @Test
    public void parse_Ok() {
        testListLines.add("type,fruit,quantity");
        testListLines.add("b,banana,20");
        testListLines.add("b,apple,100");
        expected.add(new Transaction("b", new Fruit("banana"), 20));
        expected.add(new Transaction("b", new Fruit("apple"), 100));
        actual = parserServiceTest.parse(testListLines);
        assertEquals(expected, actual);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void parseWithoutFruit_notOk() {
        testListLines.add("type,fruit,quantity");
        testListLines.add("b,20");
        testListLines.add("b,apple,100");
        expected.add(new Transaction("b", null, 20));
        expected.add(new Transaction("b", new Fruit("apple"), 100));
        actual = parserServiceTest.parse(testListLines);
        assertEquals(expected, actual);

    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void parseWithoutCount_notOk() {
        testListLines.add("type,fruit,quantity");
        testListLines.add("banana,20");
        testListLines.add("b,apple,100");
        expected.add(new Transaction(null, new Fruit("banana"), 20));
        expected.add(new Transaction("b", new Fruit("apple"), 100));
        actual = parserServiceTest.parse(testListLines);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void parseWithoutParameters_notOk() {
        testListLines.add("type,fruit,quantity");
        testListLines.add("b,banana");
        testListLines.add("b,apple,100");
        expected.add(new Transaction("b", new Fruit("banana"), null));
        expected.add(new Transaction("b", new Fruit("apple"), 100));
        actual = parserServiceTest.parse(testListLines);
    }

    @After
    public void clearStorage() {
        Storage.storage.clear();
    }
}
