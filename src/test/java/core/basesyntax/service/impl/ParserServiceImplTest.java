package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private ParserService parserService;
    private List<String> transactionsList;

    @Before
    public void setUp() throws Exception {
        parserService = new ParserServiceImpl();
        transactionsList = List.of("type,fruit,quantity", "b,banana,20");
    }

    @Test
    public void parse_OK() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction("b", new Fruit("banana"), 20));
        List<Transaction> actual = parserService.parse(transactionsList);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyList_parse_OK() {
        List<String> emptyList = new ArrayList<>();
        parserService.parse(emptyList);
    }

    @Test (expected = NumberFormatException.class)
    public void invalidNumberType_parse_NotOK() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("s,apple,one");
        parserService.parse(stringList);
    }

    @Test (expected = NullPointerException.class)
    public void nullStringValue_parse_NotOK() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add(null);
        parserService.parse(stringList);
    }

    @Test (expected = NullPointerException.class)
    public void null_parse_NotOK() {
        parserService.parse(null);
    }
}
