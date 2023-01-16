package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceTest {
    private static DataParserService dataParserService;

    @BeforeClass
    public static void beforeClass() {
        dataParserService = new DataParserServiceImpl();
    }

    @Test
    public void toTransaction_dataFromFile_ok() {
        List<String> dataFromFile = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual = dataParserService.toTransactions(dataFromFile);
        Assert.assertEquals("Expected " + expected + " for valid data, but was "
                + actual, expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void toTransaction_isNotValidOperation_notOk() {
        List<String> incorrectOperation = List.of("type,fruit,quantity", "a,banana,20");
        try {
            dataParserService.toTransactions(incorrectOperation);
        } catch (RuntimeException e) {
            throw new RuntimeException("RuntimeException should be thrown -"
                    + " incorrect Operation", e);
        }
        Assert.fail();
    }

    @Test(expected = RuntimeException.class)
    public void toTransaction_dataFromEmptyFile_notOk() {
        List<String> empty = new ArrayList<>();
        try {
            dataParserService.toTransactions(empty);
        } catch (RuntimeException e) {
            throw new RuntimeException("RuntimeException should be thrown -"
                    + " List<String> don't contains data", e);
        }
    }
}
