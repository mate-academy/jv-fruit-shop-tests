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
    private static final List<String> DATA_FROM_FILE = List.of("type,fruit,quantity", "b,banana,20",
            "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
            "p,apple,20", "p,banana,5", "s,banana,50");

    @BeforeClass
    public static void beforeClass() {
        dataParserService = new DataParserServiceImpl();
    }

    @Test
    public void parserDataFromFile_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual = dataParserService.toTransactions(DATA_FROM_FILE);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void parserDataFromFile_IsNotValidOperation_NotOk() {
        List<String> incorrectOperation = List.of("type,fruit,quantity", "a,banana,20");
        try {
            dataParserService.toTransactions(incorrectOperation);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("RuntimeException should be thrown - incorrect Operation");
    }

    @Test
    public void parserDataFromEmptyFile_NotOk() {
        List<String> empty = new ArrayList<>();
        try {
            dataParserService.toTransactions(empty);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("RuntimeException should be thrown - List<String> don't contains data");
    }
}
