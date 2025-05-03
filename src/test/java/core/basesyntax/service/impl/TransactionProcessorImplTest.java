package core.basesyntax.service.impl;

import static org.mockito.Mockito.verify;

import core.basesyntax.exceptions.InvalidOperationException;
import core.basesyntax.model.FruitsTransaction;
import core.basesyntax.service.StorageService;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransactionProcessorImplTest {
    private TransactionProcessorImpl transactionProcessor;

    @Mock
    private StorageService storageService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionProcessor = new TransactionProcessorImpl(storageService);
    }

    @Test
    public void executeTransactionsTest_BalanceOperation() {
        List<FruitsTransaction> transactions = Arrays.asList(
                new FruitsTransaction("b", "apple", 10),
                new FruitsTransaction("b", "banana", 20)
        );

        transactionProcessor.executeTransactions(transactions);

        verify(storageService).add("apple", 10);
        verify(storageService).add("banana", 20);
    }

    @Test
    public void executeTransactionsTest_SupplyOperation() {
        List<FruitsTransaction> transactions = Arrays.asList(
                new FruitsTransaction("s", "apple", 10),
                new FruitsTransaction("s", "banana", 20)
        );

        transactionProcessor.executeTransactions(transactions);

        verify(storageService).updateQuantity("apple", 10);
        verify(storageService).updateQuantity("banana", 20);
    }

    @Test
    public void executeTransactionsTest_ReturnOperation() {
        List<FruitsTransaction> transactions = Arrays.asList(
                new FruitsTransaction("r", "apple", 5),
                new FruitsTransaction("r", "banana", 15)
        );

        transactionProcessor.executeTransactions(transactions);

        verify(storageService).updateQuantity("apple", 5);
        verify(storageService).updateQuantity("banana", 15);
    }

    @Test(expected = InvalidOperationException.class)
    public void executeTransactionsTest_InvalidOperation() {
        List<FruitsTransaction> transactions = Arrays.asList(
                new FruitsTransaction("x", "apple", 5),
                new FruitsTransaction("x", "banana", 15)
        );

        transactionProcessor.executeTransactions(transactions);
    }
}
