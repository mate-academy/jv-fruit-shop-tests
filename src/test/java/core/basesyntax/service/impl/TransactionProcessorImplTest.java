package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.handles.BallanceHandler;
import core.basesyntax.service.handles.PurchaseHandler;
import core.basesyntax.service.handles.ReturnHandler;
import core.basesyntax.service.handles.SupplyHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private static FruitTransaction transaction1 = new FruitTransaction(FruitTransaction
            .Operation.BALANCE,"apple", 100);
    private static FruitTransaction transaction2 = new FruitTransaction(FruitTransaction
            .Operation.PURCHASE, "banana", 50);
    private static FruitTransaction transaction3 = new FruitTransaction(FruitTransaction
            .Operation.SUPPLY, "banana", 150);
    private static TransactionProcessor transactionProcessor;

    @BeforeAll
    static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BallanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());

        OperationStrategy strategy = new OperationStrategyImpl(operationHandlerMap);
        transactionProcessor = new TransactionProcessorImpl(strategy);
    }

    @Test
    void transactionProcessor_writeInput_ok() {
        List<FruitTransaction> transactionList = List.of(transaction1, transaction2, transaction3);
        assertDoesNotThrow(() -> transactionProcessor.process(transactionList));
    }
}
