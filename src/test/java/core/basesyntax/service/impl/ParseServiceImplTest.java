package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParserService parserService;

    @BeforeClass
    public static void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseToBalance_ok() {
        List<String> stringList = new ArrayList<>();
        stringList.add("b,banana,10");
        assertEquals("Expected another value b,banana,10",
                new Transaction("b", new Fruit("banana"), 10),
                parserService.parseToTransaction(stringList).get(0));
    }

    @Test
    public void parseToPurchase_ok() {
        List<String> stringList = new ArrayList<>();
        stringList.add("p,mango,228");
        assertEquals("Expected another value p,banana,10",
                new Transaction("p", new Fruit("mango"), 228),
                parserService.parseToTransaction(stringList).get(0));
    }

    @Test
    public void parseToReturn_ok() {
        List<String> stringList = new ArrayList<>();
        stringList.add("r,apple,13");
        assertEquals("Expected another value r,apple,13",
                new Transaction("r", new Fruit("apple"), 13),
                parserService.parseToTransaction(stringList).get(0));
    }

    @Test
    public void parseToSupply_ok() {
        List<String> stringList = new ArrayList<>();
        stringList.add("s,(fruit),1");
        assertEquals("Expected another value s,(fruit),1",
                new Transaction("s", new Fruit("(fruit)"), 1),
                parserService.parseToTransaction(stringList).get(0));
    }

    @Test
    public void parseStringList_ok() {
        List<String> stringList = new ArrayList<>();
        stringList.add("b,banana,10");
        stringList.add("p,mango,228");
        stringList.add("r,apple,13");
        stringList.add("s,(fruit),1");
        List<Transaction> expectedList = new ArrayList<>();
        expectedList.add(new Transaction("b", new Fruit("banana"), 10));
        expectedList.add(new Transaction("p", new Fruit("mango"), 228));
        expectedList.add(new Transaction("r", new Fruit("apple"), 13));
        expectedList.add(new Transaction("s", new Fruit("(fruit)"), 1));
        assertEquals("Expected another length ",
                expectedList.size(),
                parserService.parseToTransaction(stringList).size());
    }

    @Test
    public void parseEmptyList_ok() {
        List<String> emptyList = new ArrayList<>();
        assertEquals(
                0,
                parserService.parseToTransaction(emptyList).size());
    }
}
