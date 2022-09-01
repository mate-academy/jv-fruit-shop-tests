package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

public class ParserServiceImplTest {
    private static ParserService parserService;

    private static List<String> READ_DATA =
            List.of("type,fruit,quantity",
                    "b,banana,20",
                    "b,apple,100",
                    "s,banana,100",
                    "p,banana,13",
                    "r,apple,10",
                    "p,apple,20",
                    "p,banana,5",
                    "s,banana,50");
    private static List<FruitTransaction> EXPECTED_RESULT = List.of(
        new FruitTransaction (FruitTransaction.Operation.BALANCE, "banana", 20),
        new FruitTransaction (FruitTransaction.Operation.BALANCE, "apple", 100),
        new FruitTransaction (FruitTransaction.Operation.SUPPLY, "banana", 100),
        new FruitTransaction (FruitTransaction.Operation.PURCHASE, "banana", 13),
        new FruitTransaction (FruitTransaction.Operation.RETURN, "apple", 10),
        new FruitTransaction (FruitTransaction.Operation.PURCHASE, "apple", 20),
        new FruitTransaction (FruitTransaction.Operation.PURCHASE, "banana", 5),
        new FruitTransaction (FruitTransaction.Operation.SUPPLY, "banana", 50));

    @BeforeClass
    public static void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_correctData_ok() {
        List<FruitTransaction> actual = parserService.parse(READ_DATA);
        Assert.assertEquals(EXPECTED_RESULT, actual);
    }

    @Test
    public void parse_getTransaction_ok() {
        String[] line = {"b,banana,20"};
        FruitTransaction.Operation transaction = FruitTransaction.Operation.BALANCE;
        ParserServiceImpl.getTransaction(line);
        Assert.assertEquals(transaction, line);
    }
}