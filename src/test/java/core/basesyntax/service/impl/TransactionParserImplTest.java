package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static TransactionParser transactionParser;
    private List<String> list;
    private List<Transaction> transactionList;

    @BeforeClass
    public static void beforeAll() {
        transactionParser = new TransactionParserImpl();
    }

    @Before
    public void setUp() {
        list = List.of("type,fruit,quantity",
                "b,mango,80",
                "b,pineapple,30",
                "p,mango,20",
                "p,pineapple,5",
                "s,mango,40",
                "s,pineapple,10");
        transactionList = new ArrayList<>();
        transactionList.add(new Transaction(Transaction.Operation.BALANCE,
                new Fruit("mango"), 80));
        transactionList.add(new Transaction(Transaction.Operation.BALANCE,
                new Fruit("pineapple"), 30));
        transactionList.add(new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("mango"), 20));
        transactionList.add(new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("pineapple"), 5));
        transactionList.add(new Transaction(Transaction.Operation.SUPPLY,
                new Fruit("mango"), 40));
        transactionList.add(new Transaction(Transaction.Operation.SUPPLY,
                new Fruit("pineapple"), 10));

    }

    @Test
    public void service_parserParse_ok() {
        List<Transaction> actual = transactionParser.parse(list);
        List<Transaction> expected = transactionList;
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = NullPointerException.class)
    public void service_parserParseNull_ok() {
        list = null;
        transactionParser.parse(list);
    }
}
