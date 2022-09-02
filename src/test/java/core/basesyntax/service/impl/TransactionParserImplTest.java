package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private List<String> testParsedData;
    private TransactionParser transactionParser;

    @Before
    public void setUp() {
        testParsedData = new ArrayList<>();
        transactionParser = new TransactionParserImpl();
        testParsedData.add("type,fruit,quantity");
        testParsedData.add("b,banana,20");
        testParsedData.add("b,apple,100");
        testParsedData.add("s,banana,100");
    }

    @Test
    public void transactionsParsing_Ok() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction("b", new Fruit("banana"), 20));
        expected.add(new Transaction("b", new Fruit("apple"), 100));
        expected.add(new Transaction("s", new Fruit("banana"), 100));
        List<Transaction> actual = transactionParser.transactionParser(testParsedData);
        Assert.assertEquals(expected.get(0).getFruit(), actual.get(0).getFruit());
        Assert.assertEquals(expected.get(1).getQuantity(), actual.get(1).getQuantity());
        Assert.assertEquals(expected.get(2).getOperation(), actual.get(2).getOperation());
    }
}
