package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionsServiceImplTest {
    private TransactionService transactionService;

    @Before
    public void setUp() {
        transactionService = new TransactionsServiceImpl();
    }

    @Test
    public void transactoinServise_Ok() {
        List<String> dataFromFile = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN,"apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"banana", 50));
        List<FruitTransaction> actual = transactionService.getlistOfFruitTransaction(dataFromFile);
        assertEquals(expected, actual);
    }
}
