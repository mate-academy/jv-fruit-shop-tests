package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.FruitTransactionParser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser fruitTransactionParser;
    private final List<String> stringList = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void parse_CorrectDate_Ok() {
        stringList.add("type,fruit,quantity");
        stringList.add("b,banana,100");
        stringList.add("s,banana,20");
        stringList.add("r,banana,10");
        stringList.add("p,banana,10");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE, "banana", 100));
        expected.add(new FruitTransaction(Operation.SUPPLY, "banana", 20));
        expected.add(new FruitTransaction(Operation.RETURN, "banana", 10));
        expected.add(new FruitTransaction(Operation.PURCHASE, "banana", 10));
        List<FruitTransaction> actual = fruitTransactionParser.parse(stringList);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_EmptyList_NotOk() {
        fruitTransactionParser.parse(stringList);
    }

    @Test(expected = RuntimeException.class)
    public void parse_LessColumnDate_NotOk() {
        stringList.add("b,banana");
        stringList.add("s,banana");
        stringList.add("r,banana");
        stringList.add("p,banana");
        fruitTransactionParser.parse(stringList);
    }

    @Test(expected = RuntimeException.class)
    public void parse_MoreColumnDate_NotOk() {
        stringList.add("b,banana,100,Africa");
        stringList.add("s,banana,20,Africa");
        stringList.add("r,banana,10,Africa");
        stringList.add("p,banana,10,Africa");
        fruitTransactionParser.parse(stringList);
    }

    @Test(expected = RuntimeException.class)
    public void parse_NegativeValueQuantity_NotOk() {
        stringList.add("b,banana,-10");
        stringList.add("s,banana,-20");
        stringList.add("r,banana,-30");
        stringList.add("p,banana,-40");
        fruitTransactionParser.parse(stringList);
    }
}
