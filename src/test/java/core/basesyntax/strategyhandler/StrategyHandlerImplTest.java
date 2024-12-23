package core.basesyntax.strategyhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StrategyHandlerImplTest {
    private StrategyHandlerImpl strategyHandler;
    private Map<FruitTransaction.Operation, OperationHandler> strategyMap;
    private List<FruitTransaction> handledTransactions;

    @BeforeEach
    void setUp() {
        strategyHandler = new StrategyHandlerImpl();
        handledTransactions = new ArrayList<>();
        OperationHandler balanceHandler = transaction -> handledTransactions.add(transaction);
        OperationHandler purchaseHandler = transaction -> handledTransactions.add(transaction);
        OperationHandler returnHandler = transaction -> handledTransactions.add(transaction);
        OperationHandler supplyHandler = transaction -> handledTransactions.add(transaction);
        strategyMap = Map.of(
                FruitTransaction.Operation.BALANCE, balanceHandler,
                FruitTransaction.Operation.PURCHASE, purchaseHandler,
                FruitTransaction.Operation.RETURN, returnHandler,
                FruitTransaction.Operation.SUPPLY, supplyHandler
        );
    }

    @Test
    void strategyHandler_validOperation_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction("banana", 100, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 20, FruitTransaction.Operation.PURCHASE),
                new FruitTransaction("banana", 15, FruitTransaction.Operation.RETURN),
                new FruitTransaction("banana", 50, FruitTransaction.Operation.SUPPLY)
        );
        strategyHandler.strategyHandler(strategyMap, transactions);
        assertEquals(4, handledTransactions.size());
        assertEquals(transactions, handledTransactions);
    }

    @Test
    void strategyHandler_invalidOperation_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction("banana", 100, FruitTransaction.Operation.BALANCE),
                new FruitTransaction("banana", 100, null)
        );

        assertThrows(NullPointerException.class,
                () -> strategyHandler.strategyHandler(strategyMap, transactions));
    }
}
