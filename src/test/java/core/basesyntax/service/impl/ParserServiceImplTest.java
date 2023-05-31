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

    @Test
    public void parse_FindTransaction_ok() {
        transactions.add(new Transaction("r", new Fruit("apple"), 10));
        lines.add("b,banana,25");
        lines.add("r,apple,10");
        List<Transaction> expected = transactions;
        List<Transaction> actual = parserService.parse(lines);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_NullInput_ThrowRuntimeException() {
        parserService.parse(null);
    }

    @After
    public void tearDown() {
        lines.clear();
        transactions.clear();
    }
}
