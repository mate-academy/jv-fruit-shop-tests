package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
    public void createTransactions_nullFileData_NotOk() {
        transactionService.createTransactions(null);
        fail("An error was expected in case of null file data source");
    }

    @Test(expected = RuntimeException.class)
    public void createTransactions_emptyFileData_NotOk() {
        transactionService.createTransactions(fileData);
        fail("An error was expected in case of empty file data source");
    }

    @Test(expected = RuntimeException.class)
    public void createTransactions_wrongTransactionOperation_NotOk() {
        fileData.add("g,banana,20");
        transactionService.createTransactions(fileData);
        fail("An error was expected in case of wrong transaction operation type");
    }

    @Test(expected = RuntimeException.class)
    public void createTransactions_wrongTransactionFruitName_NotOk() {
        fileData.add("s,1233,20");
        transactionService.createTransactions(fileData);
        fail("An error was expected in case of wrong transaction fruit name");
    }

    @Test(expected = RuntimeException.class)
    public void createTransactions_wrongTransactionFruitQuantity_NotOk() {
        fileData.add("s,banana,-10");
        transactionService.createTransactions(fileData);
        fail("An error was expected in case of wrong transaction fruit quantity");
    }

    @Test
    public void createTransactions_correctFileData_Ok() {
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
    public void createTransactions_checkTransactionsSize_Ok() {
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
