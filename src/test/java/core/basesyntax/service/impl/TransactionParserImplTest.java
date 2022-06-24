package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void parse_Ok() {
        List<FruitTransaction> actual = transactionParser.parseFile(List.of("skip Line",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100"));
        List<FruitTransaction> expected = List.of(new FruitTransaction("b", "banana", 20),
                new FruitTransaction("b", "apple", 100),
                new FruitTransaction("s", "banana", 100));
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void anotherTypeOperation_NotOk() {
        transactionParser.parseFile(List.of("skip Line", "f,banana,20"));
    }

    @Test(expected = RuntimeException.class)
    public void negativeQuantity_NotOk() {
        transactionParser.parseFile(List.of("skip Line", "p,banana,-20"));
    }

    @Test(expected = RuntimeException.class)
    public void nullLine_notOk() {
        transactionParser.parseFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void fruitIsBlank_NotOk() {
        transactionParser.parseFile(List.of("skip Line", "p, ,20"));
    }
}
