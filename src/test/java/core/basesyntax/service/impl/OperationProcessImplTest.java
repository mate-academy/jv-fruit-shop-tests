package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationProcess;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationProcessImplTest {
    private OperationProcess operationProcess;
    private List<FruitTransaction> handledTransactions;

    @BeforeEach
    void setUp() {
        OperationHandler operationHandler = fruitTransaction
                -> handledTransactions.add(fruitTransaction);
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, operationHandler);
        handlers.put(Operation.SUPPLY, operationHandler);
        handlers.put(Operation.PURCHASE, operationHandler);
        handlers.put(Operation.RETURN, operationHandler);
        handledTransactions = new ArrayList<>();
        OperationStrategy operationStrategy = new OperationStrategy(handlers);
        operationProcess = new OperationProcessImpl(operationStrategy);
    }

    @Test
    void processTransaction_callCorrectOperationHandlerTransactions() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(Operation.BALANCE);
        balanceTransaction.setFruit("apple");
        balanceTransaction.setQuantity(10);
        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(Operation.BALANCE);
        supplyTransaction.setFruit("banana");
        supplyTransaction.setQuantity(20);
        operationProcess.processTransaction(List.of(balanceTransaction, supplyTransaction));
        assertEquals(2, handledTransactions.size());
        assertEquals(balanceTransaction, handledTransactions.get(0));
        assertEquals(supplyTransaction, handledTransactions.get(1));
    }
}
