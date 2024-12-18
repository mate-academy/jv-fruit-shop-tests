package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ShopServiceImplTest {
    @Mock
    private OperationStrategy operationStrategy;

    @Mock
    private OperationHandler balanceHandler;

    @Mock
    private OperationHandler purchaseHandler;

    private ShopServiceImpl shopService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    public void testProcess_withValidTransactions_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50);

        List<FruitTransaction> transactions = List.of(balanceTransaction, purchaseTransaction);

        when(operationStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE))
                .thenReturn(balanceHandler);
        when(operationStrategy.getOperationHandler(FruitTransaction.Operation.PURCHASE))
                .thenReturn(purchaseHandler);

        shopService.process(transactions);

        verify(operationStrategy).getOperationHandler(FruitTransaction.Operation.BALANCE);
        verify(operationStrategy).getOperationHandler(FruitTransaction.Operation.PURCHASE);
        verify(balanceHandler).doOperation("apple", 100);
        verify(purchaseHandler).doOperation("apple", 50);
    }

    @Test
    public void testProcess_withMissingHandler_notOk() {
        FruitTransaction unsupportedTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 30);
        List<FruitTransaction> transactions = List.of(unsupportedTransaction);

        when(operationStrategy.getOperationHandler(FruitTransaction.Operation.RETURN))
                .thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> shopService.process(transactions),
                "Expected exception for unsupported operation"
        );

        assertTrue(exception.getMessage().contains("No handler found for operation: RETURN"),
                "Exception message should mention missing handler for RETURN operation");
    }

    @Test
    public void testProcess_withEmptyTransactionList_ok() {
        List<FruitTransaction> emptyTransactions = List.of();

        shopService.process(emptyTransactions);

        verifyNoInteractions(operationStrategy);
    }
}
