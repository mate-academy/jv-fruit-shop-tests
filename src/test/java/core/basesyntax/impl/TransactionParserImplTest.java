package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void before() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void validTransactionParser_Ok() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,120");
        stringList.add("p,banana,25");
        List<FruitTransaction> actual = transactionParser.parse(stringList);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE,"banana", 120));
        expected.add(new FruitTransaction(Operation.PURCHASE,"banana", 25));
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void quantityIsNull_NotOk() {
        List<String> strList = new ArrayList<>();
        strList.add("type,fruit,quantity");
        strList.add("b,banana,100");
        strList.add("p,apple,50");
        strList.add("p,apple,null");
        transactionParser.parse(strList);
    }

    @Test (expected = RuntimeException.class)
    public void quantityIsEmpty_notOk() {
        List<String> stringList = new ArrayList<>();
        stringList.add("type,fruit,quantity");
        stringList.add("p,apple,100");
        stringList.add("s,apple,200");
        stringList.add("r,apple,");
        transactionParser.parse(stringList);
    }
}
