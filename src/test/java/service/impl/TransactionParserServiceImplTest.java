package service.impl;

import java.util.List;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.TransactionParserService;

public class TransactionParserServiceImplTest {
    private static final String VALID_FRUITS_DATA = "b,banana,20";
    private static final String INVALID_FRUITS_DATA = "b,banana,-20";
    private static TransactionParserService transactionParserService;

    @BeforeClass
    public static void beforeAll() {
        transactionParserService = new TransactionParserServiceImpl();
    }

    @Test
    public void parse_validData_Ok() {
        List<String> fruitsData = List.of(VALID_FRUITS_DATA);
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

    @Test(expected = IllegalArgumentException.class)
    public void parse_negativeQuantity_NotOk() {
        List<String> fruitsData = List.of(INVALID_FRUITS_DATA);
        transactionParserService.parse(fruitsData);
    }
}
