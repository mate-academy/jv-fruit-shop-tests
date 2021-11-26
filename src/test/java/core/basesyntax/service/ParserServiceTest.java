package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.impl.ParseServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static ParserService parserService;
    private static List<String> actual;
    private static List<TransactionDto> expected;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParseServiceImpl();
        actual = new ArrayList<>();
        expected = new ArrayList<>();
    }

    @Before
    public void setUp() {
        actual.add("some info");
        actual.add("b,banana,20");
        actual.add("b,apple,100");
        actual.add("s,banana,70");
        actual.add("p,apple,17");
        expected.add(new TransactionDto(Operation.BALANCE, new Fruit("banana"), 20));
        expected.add(new TransactionDto(Operation.BALANCE, new Fruit("apple"), 100));
        expected.add(new TransactionDto(Operation.SUPPLY, new Fruit("banana"), 70));
        expected.add(new TransactionDto(Operation.PURCHASE, new Fruit("apple"), 17));
    }

    @After
    public void tearDown() {
        actual.clear();
        expected.clear();
    }

    @Test
    public void validParse_Ok() {
        Assert.assertEquals(expected, parserService.parseData(actual));
    }
}
