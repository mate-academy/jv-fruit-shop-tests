package core.basesyntax;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.impl.ShopServiceImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private OperationStrategy operationStrategy;
    private OperationHandler balanceHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler returnHandler;
    private OperationHandler supplyHandler;

    @BeforeEach
    void setUp() {
        operationStrategy = mock(OperationStrategy.class);
        balanceHandler = mock(OperationHandler.class);
        purchaseHandler = mock(OperationHandler.class);
        returnHandler = mock(OperationHandler.class);
        supplyHandler = mock(OperationHandler.class);
        when(operationStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE))
                .thenReturn(balanceHandler);
        when(operationStrategy.getOperationHandler(FruitTransaction.Operation.PURCHASE))
                .thenReturn(purchaseHandler);
        when(operationStrategy.getOperationHandler(FruitTransaction.Operation.RETURN))
                .thenReturn(returnHandler);
        when(operationStrategy.getOperationHandler(FruitTransaction.Operation.SUPPLY))
                .thenReturn(supplyHandler);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 15)
        );
        shopService.process(transactions);

        verify(balanceHandler, times(1)).apply(transactions.get(0));
        verify(purchaseHandler, times(1)).apply(transactions.get(1));
        verify(returnHandler, times(1)).apply(transactions.get(2));
        verify(supplyHandler, times(1)).apply(transactions.get(3));
    }

    @Test
    void process_emptyList_ok() {
        List<FruitTransaction> emptyTransactions = List.of();
        shopService.process(emptyTransactions);
        verify(operationStrategy, never()).getOperationHandler(any());
    }

    @Test
    void getOperationHandler_allOperations_ok() {
        for (FruitTransaction.Operation operation : FruitTransaction.Operation.values()) {
            Assertions.assertNotNull(operationStrategy.getOperationHandler(operation),
                    "Handler is missing for operation: " + operation);
        }
    }
}
