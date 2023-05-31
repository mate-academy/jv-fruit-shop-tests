package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParseServiceTest {
    private ParseServiceImpl parserService;
    private List<String> transactionsList;

    @Before
    public void setUp() {
        parserService = new ParseServiceImpl();
        transactionsList = List.of("type,fruit,quantity", "b,apple,30");
    }

    @Test
    public void parse_Ok() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction("b",
                new Fruit("apple"), 30));
        List<Transaction> actual = parserService.transactionsParser(transactionsList);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NumberFormatException.class)
    public void parse_NotOk() {
        List<String> result = List.of("type,fruit,quantity", "b, apple, three");
        parserService.transactionsParser(result);
    }
}
