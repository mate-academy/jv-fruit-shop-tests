package core.basesyntax.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private OperationHandler balanceHandler;
    private OperationHandler supplyHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler returnHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = mock(OperationHandler.class);
        supplyHandler = mock(OperationHandler.class);
        purchaseHandler = mock(OperationHandler.class);
        returnHandler = mock(OperationHandler.class);

        OperationStrategy operationStrategy = mock(OperationStrategy.class);
        when(operationStrategy.getHandler(Operation.BALANCE)).thenReturn(balanceHandler);
        when(operationStrategy.getHandler(Operation.SUPPLY)).thenReturn(supplyHandler);
        when(operationStrategy.getHandler(Operation.PURCHASE)).thenReturn(purchaseHandler);
        when(operationStrategy.getHandler(Operation.RETURN)).thenReturn(returnHandler);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(Operation.BALANCE, "banana", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 50),
                new FruitTransaction(Operation.PURCHASE, "banana", 30),
                new FruitTransaction(Operation.RETURN, "banana", 10)
        );

        shopService.process(transactions);

        verify(balanceHandler).apply(transactions.get(0));
        verify(supplyHandler).apply(transactions.get(1));
        verify(purchaseHandler).apply(transactions.get(2));
        verify(returnHandler).apply(transactions.get(3));

        verifyNoMoreInteractions(balanceHandler, supplyHandler, purchaseHandler, returnHandler);
    }
}
