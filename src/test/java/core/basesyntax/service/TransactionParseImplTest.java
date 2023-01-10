package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.TransactionParseImpl;
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
}
