package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService parserService;
    private List<String> transactionsList;

    @BeforeClass
    public static void beforeClass() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_ok() {
        transactionsList = List.of("type,fruit,quantity", "b,banana,20");
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction("b", new Fruit("banana"), 20));
        List<Transaction> actual = parserService.parse(transactionsList);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_emptyList_ok() {
        List<Transaction> expected = new ArrayList<>();
        List<String> emptyList = new ArrayList<>();
        List<Transaction> actual = parserService.parse(emptyList);
        assertEquals(expected, actual);

    }

    @Test (expected = NumberFormatException.class)
    public void parse_invalidNumberType_notOk() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("s,apple,one");
        parserService.parse(stringList);
    }

    @Test (expected = NullPointerException.class)
    public void parse_nullStringValue_notOk() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add(null);
        parserService.parse(stringList);
    }

    @Test (expected = NullPointerException.class)
    public void parse_null_notOk() {
        parserService.parse(null);
    }
}
