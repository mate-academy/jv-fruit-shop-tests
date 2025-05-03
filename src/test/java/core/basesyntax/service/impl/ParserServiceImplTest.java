package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;
    private static List<String> lines;
    private static List<Transaction> transactions;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
        lines = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    @Test(expected = RuntimeException.class)
    public void parse_inputIsEmpty_isNotValid() {
        parserService.parse(null);
    }

    @Test
    public void parse_skipFirstLine_isValid() {
        transactions.add(new Transaction("s", new Fruit("apple"), 10));
        lines.add("b,banana,10");
        lines.add("s,apple,10");
        List<Transaction> expected = transactions;
        List<Transaction> actual = parserService.parse(lines);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_correctOutput_isValid() {
        transactions.add(new Transaction("s", new Fruit("apple"), 10));
        transactions.add(new Transaction("r", new Fruit("banana"), 30));
        transactions.add(new Transaction("p", new Fruit("apple"), 15));
        lines.add("b,banana,10");
        lines.add("s,apple,10");
        lines.add("r,banana,30");
        lines.add("p,apple,15");
        List<Transaction> expected = transactions;
        List<Transaction> actual = parserService.parse(lines);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        lines.clear();
        transactions.clear();
    }
}
