package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static ParserService parserService;

    @BeforeClass
    public static void init() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseFile_Ok() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction("b", new Fruit("banana"), 20));
        expected.add(new Transaction("b", new Fruit("apple"), 100));
        expected.add(new Transaction("s", new Fruit("banana"), 100));
        List<Transaction> actual = parserService.parse(lines);
        assertEquals(expected.get(0), actual.get(0));
    }

    @Test
    public void parseNonExistentFile_NotOk() {
        List<String> lines = new ArrayList<>();
        parserService.parse(lines);
    }
}
