package core.basesyntax.service.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @Mock
    private OperationStrategy mockStrategy;

    @Mock
    private OperationHandler mockHandler;

    @InjectMocks
    private ShopServiceImpl shopService;

    @Test
    void process_balanceTransaction_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 40);
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 30);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10);
        List<FruitTransaction> transactions =
                List.of(balanceTransaction,returnTransaction,
                        purchaseTransaction,supplyTransaction);
        when(mockStrategy.getHandlerForTransaction(any(FruitTransaction.Operation.class)))
                .thenReturn(mockHandler);
        shopService.process(transactions);
        verify(mockStrategy, times(1))
                .getHandlerForTransaction(FruitTransaction.Operation.BALANCE);
        verify(mockStrategy, times(1))
                .getHandlerForTransaction(FruitTransaction.Operation.RETURN);
        verify(mockStrategy, times(1))
                .getHandlerForTransaction(FruitTransaction.Operation.PURCHASE);
        verify(mockStrategy, times(1))
                .getHandlerForTransaction(FruitTransaction.Operation.SUPPLY);
        verify(mockHandler, times(1))
                .doTransaction(balanceTransaction.getFruit(), balanceTransaction.getQuantity());
        verify(mockHandler, times(1))
                .doTransaction(returnTransaction.getFruit(), returnTransaction.getQuantity());
        verify(mockHandler, times(1))
                .doTransaction(purchaseTransaction.getFruit(), purchaseTransaction.getQuantity());
        verify(mockHandler, times(1))
                .doTransaction(supplyTransaction.getFruit(), supplyTransaction.getQuantity());
        verifyNoMoreInteractions(mockHandler);
    }
}
