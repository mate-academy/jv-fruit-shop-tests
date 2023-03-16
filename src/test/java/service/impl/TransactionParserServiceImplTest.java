package service.impl;

import java.util.List;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.TransactionParserService;

public class TransactionParserServiceImplTest {
    private static TransactionParserService transactionParserService;

    @BeforeClass
    public static void beforeAll() {
        transactionParserService = new TransactionParserServiceImpl();
    }

    @Test
    public void parse_validData_Ok() {
        List<String> fruitsData = List.of("b,banana,20");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        List<FruitTransaction> actual = transactionParserService.parse(fruitsData);
        Assert.assertEquals("Data wasn't parsed correctly!",
                expected.get(0).getFruit(), actual.get(0).getFruit());
        Assert.assertEquals("Data wasn't parsed correctly!",
                expected.get(0).getQuantity(), actual.get(0).getQuantity());
        Assert.assertEquals("Data wasn't parsed correctly!",
                expected.get(0).getOperation(), actual.get(0).getOperation());
    }
}
