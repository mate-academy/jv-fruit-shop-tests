package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.models.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static TransactionServiceImpl transactionService;

    @BeforeClass
    public static void beforeClass() {
        transactionService = new TransactionServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void createTransaction_FromNullList() {
        transactionService.createTransactionsList(null);
    }

    @Test(expected = RuntimeException.class)
    public void createTransaction_FromEmptyList() {
        transactionService.createTransactionsList(List.of("", "", ""));
    }

    @Test
    public void createTransaction_Ok() {
        List<String> actualInput = new ArrayList<>(List.of("type,fruit,quantity",
                "b,banana,10",
                "b,apple,20"));
        List<FruitTransaction> expected = new ArrayList<>(List.of(
                new FruitTransaction("b", "banana", 10),
                new FruitTransaction("b", "apple", 20)));
        List<FruitTransaction> actual = transactionService.createTransactionsList(actualInput);
        assertEquals(expected, actual);
    }
}
