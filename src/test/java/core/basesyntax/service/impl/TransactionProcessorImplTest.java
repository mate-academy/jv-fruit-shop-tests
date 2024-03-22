package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.PurchaseOperation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransactionProcessorImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String UNSUPPORTED_OPERATION = "x";
    private static final String BALANCE_OPERATION = "b";
    private static final int QUANTITY = 50;
    private static final String UNSUPPORTED_OPERATION_ERROR_MESSAGE = "Invalid operation code: ";
    @Mock
    private OperationStrategy operationStrategy;
    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionProcessor = new TransactionProcessorImpl(operationStrategy);
    }

    @Test
    void executeTransactions_NoInputTransactions_Ok() {
        transactionProcessor.executeTransactions(new ArrayList<>());
        verifyNoInteractions(operationStrategy);
    }

    @Test
    void executeTransactions_OneInputTransaction_Ok() {
        var transaction = new Transaction(BALANCE_OPERATION, BANANA, 100);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        OperationHandler handler = mock(OperationHandler.class);
        when(operationStrategy.getHandler(transaction)).thenReturn(handler);

        transactionProcessor.executeTransactions(transactions);

        verify(operationStrategy).getHandler(transaction);
        verify(handler).handle(transaction);
    }

    @Test
    void executeTransactions_MultipleTransactions_Ok() {
        var balanceTransaction = new Transaction("b", BANANA, QUANTITY);
        var purchaseTransaction = new Transaction("p", APPLE, QUANTITY);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(balanceTransaction);
        transactions.add(purchaseTransaction);

        var balanceOperation = mock(BalanceOperation.class);
        when(operationStrategy.getHandler(balanceTransaction)).thenReturn(balanceOperation);

        var purchaseOperation = mock(PurchaseOperation.class);
        when(operationStrategy.getHandler(purchaseTransaction)).thenReturn(purchaseOperation);

        transactionProcessor.executeTransactions(transactions);

        verify(operationStrategy).getHandler(balanceTransaction);
        verify(operationStrategy).getHandler(purchaseTransaction);
        verify(balanceOperation).handle(balanceTransaction);
        verify(purchaseOperation).handle(purchaseTransaction);
    }

    @Test
    void executeTransactions_UnsupportedOperation_ThrowsException() {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                new Transaction(UNSUPPORTED_OPERATION, BANANA, QUANTITY));
        assertEquals(UNSUPPORTED_OPERATION_ERROR_MESSAGE + UNSUPPORTED_OPERATION,
                exception.getMessage());
    }
}
