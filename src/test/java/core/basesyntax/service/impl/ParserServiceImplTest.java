package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.LinkedList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static List<FruitTransaction> expected;
    private static List<FruitTransaction> actual;
    private static ParserService parserService;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
        expected = new LinkedList<>(List.of(
                new FruitTransaction(BALANCE, "banana",20),
                new FruitTransaction(BALANCE, "apple",100),
                new FruitTransaction(SUPPLY, "banana",100)));
    }

    @Test
    public void parse_stringToFruit_Ok() {
        List<String> list = new LinkedList<>(List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,100", "s,banana,100"));
        actual = parserService.parse(list);
        assertTrue(expected.size() == actual.size()
                && expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test(expected = RuntimeException.class)
    public void parse_stringToFruit_NotOk() {
        List<String> list = new LinkedList<>(List.of("typefruitquantity",
                "bbanana20", "bapple100", "sbanana00"));
        parserService.parse(list);
    }

    @Test
    public void parse_stringHead_Ok() {
        List<String> list = new LinkedList<>(List.of("type,fruit,quantity"));
        List<FruitTransaction> parse = parserService.parse(list);
        assertTrue("Test failed! Should be empty, but was: " + parse, parse.isEmpty());
    }

    @Test
    public void parse_sizeFruitList_Ok() {
        List<String> list = new LinkedList<>(List.of("type,fruit,quantity",
                "b,banana,20", "b,apple,100", "s,banana,100"));
        List<FruitTransaction> parse = parserService.parse(list);
        int actual = parse.size();
        int expected = 3;
        assertEquals("Test failed! Size list should be 3 but was: " + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_ListNull_Ok() {
        parserService.parse(null);

    }

    @Test
    public void parse_emptyList_Ok() {
        List<String> list = new LinkedList<>();
        List<FruitTransaction> parse = parserService.parse(list);
        assertTrue("Test failed! Should be empty, but was: " + parse, parse.isEmpty());
    }
}
