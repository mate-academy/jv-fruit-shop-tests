package core.basesyntax.services;

import core.basesyntax.models.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TransactionProcessorTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private OperationHandler balanceHandler;
    private OperationHandler supplyHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler returnHandler;

    @BeforeEach
    void setUp() {
        operationHandlers = new HashMap<>();
        balanceHandler = Mockito.mock(OperationHandler.class);
        supplyHandler = Mockito.mock(OperationHandler.class);
        purchaseHandler = Mockito.mock(OperationHandler.class);
        returnHandler = Mockito.mock(OperationHandler.class);
        operationHandlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        operationHandlers.put(FruitTransaction.Operation.RETURN, returnHandler);
    }

    @Test
    void processTransactions_validTransaction_shouldCallHandler() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "grape", 20)
        );
        TransactionProcessor processor = new TransactionProcessor(operationHandlers);
        processor.processTransactions(transactions);
        Mockito.verify(balanceHandler).apply(transactions.get(0));
        Mockito.verify(supplyHandler).apply(transactions.get(1));
        Mockito.verify(purchaseHandler).apply(transactions.get(2));
        Mockito.verify(returnHandler).apply(transactions.get(3));
    }
}
