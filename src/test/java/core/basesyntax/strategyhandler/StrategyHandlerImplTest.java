package core.basesyntax.strategyhandler;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import core.basesyntax.storage.FruitTransaction;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StrategyHandlerImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> strategyMap;
    private StrategyHandler strategyHandlerImpl;
    private OperationHandler balanceHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler returnHandler;
    private OperationHandler supplyHandler;

    @BeforeEach
    void init() {
        strategyHandlerImpl = new StrategyHandlerImpl();
        balanceHandler = mock(BalanceHandler.class);
        purchaseHandler = mock(PurchaseHandler.class);
        returnHandler = mock(ReturnHandler.class);
        supplyHandler = mock(SupplyHandler.class);
        strategyMap = Map.of(
                FruitTransaction.Operation.BALANCE, balanceHandler,
                FruitTransaction.Operation.RETURN, returnHandler,
                FruitTransaction.Operation.SUPPLY, supplyHandler,
                FruitTransaction.Operation.PURCHASE, purchaseHandler
        );
    }

    @Test
    void strategyHAndlerImpl_validOperation_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction("banana", 100, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 10, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 100, FruitTransaction.Operation.SUPPLY),
                new FruitTransaction("banana", 10, FruitTransaction.Operation.RETURN)
        );

        strategyHandlerImpl.strategyHandler(strategyMap, fruitTransactions);
        verify(balanceHandler, times(1)).handleTransaction(fruitTransactions.get(0));
        verify(purchaseHandler, times(1)).handleTransaction(fruitTransactions.get(1));
        verify(supplyHandler, times(1)).handleTransaction(fruitTransactions.get(2));
        verify(returnHandler, times(1)).handleTransaction(fruitTransactions.get(3));
    }

    @Test
    void strategyHandlerImpl_invalidOperation_notOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction("banana", 100, null)
        );

        assertThrows(IllegalArgumentException.class,
                () -> strategyHandlerImpl.strategyHandler(strategyMap, transactions));
    }
}
