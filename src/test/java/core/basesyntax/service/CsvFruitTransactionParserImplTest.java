package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvFruitTransactionParserImplTest {
    private FruitTransactionParser parser;

    @Before
    public void beforEachTest() {
        parser = new CsvFruitTransactionParserImpl();
    }

    @Test
    public void parse_correctData_ok() {
        List<String> list = List.of("b,apple,150", "s,apple,50", "p,apple,100", "r,apple,50");
        List<FruitTransaction> expected = new ArrayList<>();
        Collections.addAll(expected,
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 150),
                new FruitTransaction(FruitTransaction.Operation.SUPLLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHES, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.RETURE, "apple", 50));
        List<FruitTransaction> actual = parser.parse(list);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parse_falseData_notOk() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "b,apple,150", "k,banana,80", "p,apple,100", "r,apple,50");
        List<FruitTransaction> expected = new ArrayList<>();
        Collections.addAll(expected,
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 150),
                new FruitTransaction(FruitTransaction.Operation.SUPLLY, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHES, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.RETURE, "apple", 50));
        List<FruitTransaction> actual = parser.parse(list);
        Assert.assertNotEquals(expected, actual);
    }
}
