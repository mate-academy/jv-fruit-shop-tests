package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParseImplTest {
    private static TransactionParser parseService;

    @BeforeClass
    public static void before() {
        parseService = new TransactionParseImpl();
    }

    @Test
    public void transactionParse_OK() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,100");
        testList.add("r,banana,50");
        List<FruitTransaction> actual = parseService.parse(testList);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.BALANCE,
                "banana", 100));
        expected.add(new FruitTransaction(Operation.RETURN,
                "banana", 50));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void transactionParse_Not_OK() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,100");
        testList.add("r,banana, null");
        parseService.parse(testList);
    }

    @Test(expected = RuntimeException.class)
    public void transactionParse_RuntimeException() {
        List<String> testList = new ArrayList<>();
        testList.add("type,fruit,quantity");
        testList.add("b,banana,100");
        testList.add("null");
        parseService.parse(testList);
    }

    @Test
    public void transactionParse_WrongList() {
        List<String> actualList = new ArrayList<>();
        actualList.add("b,banana,100");
        actualList.add("r,banana, 50");
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,100");
        expected.add("r,banana, 50");
        Assert.assertNotEquals(expected, actualList);
    }
}

