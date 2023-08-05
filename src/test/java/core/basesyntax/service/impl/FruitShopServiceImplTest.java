package core.basesyntax.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import core.basesyntax.handler.PurchaseHandlerImpl;
import core.basesyntax.handler.SupplierHandlerImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private static OperationStrategy operationStrategyMock;
    private static OperationHandler supplierHandlerMock;
    private static OperationHandler purchaseHandlerMock;

    @BeforeEach
    void setUp() {
        operationStrategyMock = mock(OperationStrategy.class);
        fruitShopService = new FruitShopServiceImpl(operationStrategyMock);
        supplierHandlerMock = mock(SupplierHandlerImpl.class);
        purchaseHandlerMock = mock(PurchaseHandlerImpl.class);
    }

    @Test
    void processData_EmptyTransactionList_ok() {
        fruitShopService.processData(Collections.emptyList());
        //Verifies that no methods were called on the mock object operationStrategyMock.
        verifyNoInteractions(operationStrategyMock);
    }

    @Test
    void processData_OneTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction.FruitBuilder()
                .setOperationType(Operation.SUPPLY)
                .setFruitName("apple")
                .setFruitQuantity(5)
                .build();
        when(operationStrategyMock.processOperation(Operation.SUPPLY))
                .thenReturn(supplierHandlerMock);
        fruitShopService.processData(Collections.singletonList(fruitTransaction));
        verify(supplierHandlerMock).applyOperation(fruitTransaction);
        // Verifies that the method processOperation was called on
        // the mock object operationStrategyMock.
        verify(operationStrategyMock).processOperation(Operation.SUPPLY);
        // Verifies that no additional methods were called on the
        // mock object operationStrategyMock
        // after the last explicit verification.
        verifyNoMoreInteractions(operationStrategyMock);
    }

    @Test
    void processData_MultipleTransactions_ok() {
        List<FruitTransaction> transactionList = new ArrayList<>();
        FruitTransaction transaction1 = new FruitTransaction.FruitBuilder()
                .setOperationType(Operation.SUPPLY)
                .setFruitName("apple")
                .setFruitQuantity(5)
                .build();
        FruitTransaction transaction2 = new FruitTransaction.FruitBuilder()
                .setOperationType(Operation.PURCHASE)
                .setFruitName("banana")
                .setFruitQuantity(3)
                .build();
        transactionList.add(transaction1);
        transactionList.add(transaction2);

        when(operationStrategyMock.processOperation(Operation.SUPPLY))
                .thenReturn(supplierHandlerMock);
        when(operationStrategyMock.processOperation(Operation.PURCHASE))
                .thenReturn(purchaseHandlerMock);

        fruitShopService.processData(transactionList);

        verify(operationStrategyMock, times(1)).processOperation(Operation.SUPPLY);
        verify(operationStrategyMock, times(1)).processOperation(Operation.PURCHASE);

        verify(supplierHandlerMock, times(1)).applyOperation(transaction1);
        verify(purchaseHandlerMock, times(1)).applyOperation(transaction2);

        verifyNoMoreInteractions(operationStrategyMock);
        verifyNoMoreInteractions(supplierHandlerMock);
        verifyNoMoreInteractions(purchaseHandlerMock);
    }
}
