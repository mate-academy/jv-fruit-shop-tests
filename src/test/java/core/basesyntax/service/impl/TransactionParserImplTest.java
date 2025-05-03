package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static List<String> testParsedData;
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void setUp() {
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
        List<Transaction> actual = transactionParser.transactionParser(testParsedData);
        Assert.assertEquals(expected.get(0).getFruit(), actual.get(0).getFruit());
        Assert.assertEquals(expected.get(0).getOperation(), actual.get(0).getOperation());
        Assert.assertEquals(expected.get(0).getQuantity(), actual.get(0).getQuantity());
    }
}
