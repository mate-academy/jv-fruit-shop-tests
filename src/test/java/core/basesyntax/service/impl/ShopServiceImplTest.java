package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitBalanceService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.BalanceOperation;
import core.basesyntax.service.strategy.impl.PurchaseOperation;
import core.basesyntax.service.strategy.impl.ReturnOperation;
import core.basesyntax.service.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShopServiceImplTest {
    @Mock
    private FruitBalanceService fruitBalanceService;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy, fruitBalanceService);
    }

    @Test
    void process_nullInputTransactions_notOk() {
        Exception exception = assertThrows(RuntimeException.class, () -> shopService.process(null));
        assertEquals("Input transactions list cannot be null", exception.getMessage());
    }

    @Test
    void process_emptyTransactionList_ok() {
        shopService.process(List.of());
        verify(fruitBalanceService, never()).updateFruitBalance(any(), anyInt());
    }

    @Test
    void process_withMultiplyTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"apple", 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"kiwi", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"kiwi", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"kiwi", 40));
        shopService.process(transactions);
        verify(fruitBalanceService, times(1))
                .updateFruitBalance("apple", 60);
        verify(fruitBalanceService, times(1))
                .updateFruitBalance("kiwi", 110);
    }
}
