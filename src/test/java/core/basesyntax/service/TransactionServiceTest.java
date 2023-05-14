package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceTest {
    private static TransactionService transactionService;
    private static List<String> fileData;

    @BeforeClass
    public static void beforeClass() {
        transactionService = new TransactionServiceImpl();
        fileData = new ArrayList<>();
    }

    @After
    public void clearData() {
        fileData.clear();
    }

    @Test(expected = RuntimeException.class)
    public void createTransactions_nullFileData_notOk() {
        transactionService.createTransactions(null);
    }

    @Test(expected = RuntimeException.class)
    public void createTransactions_emptyFileData_notOk() {
        transactionService.createTransactions(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void createTransactions_wrongTransactionOperation_notOk() {
        fileData.add("g,banana,20");
        transactionService.createTransactions(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void createTransactions_wrongTransactionFruitName_notOk() {
        fileData.add("s,1233,20");
        transactionService.createTransactions(fileData);
    }

    @Test(expected = RuntimeException.class)
    public void createTransactions_wrongTransactionFruitQuantity_notOk() {
        fileData.add("s,banana,-10");
        transactionService.createTransactions(fileData);
    }

    @Test
    public void createTransactions_correctFileData_ok() {
        fileData.add("b,banana,20");
        List<FruitTransaction> actual = transactionService.createTransactions(fileData);
        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.BALANCE;
        String expectedFruitName = "banana";
        int expectedAmount = 20;
        assertEquals(expectedOperation, actual.get(0).getOperation());
        assertEquals(expectedFruitName, actual.get(0).getFruit());
        assertEquals(expectedAmount, actual.get(0).getQuantity());
    }

    @Test
    public void createTransactions_checkTransactionsSize_ok() {
        fileData.add("b,banana,40");
        fileData.add("s,orange,10");
        fileData.add("r,apple,80");
        fileData.add("p,banana,20");
        fileData.add("");
        int expectedSize = 4;
        List<FruitTransaction> actual = transactionService.createTransactions(fileData);
        assertEquals(expectedSize, actual.size());
    }
}
