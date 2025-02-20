package core.basesyntax.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceTest {
    private OperationStrategy operationStrategy;
    private FruitShopService fruitShopService;
    private Map<Operation, OperationHandler> handlers;

    @BeforeEach
    void setUp() {
        operationStrategy = mock(OperationStrategy.class);
        fruitShopService = new FruitShopService(operationStrategy);
        handlers = new EnumMap<>(Operation.class);

        for (Operation operation : Operation.values()) {
            OperationHandler handler = mock(OperationHandler.class);
            handlers.put(operation, handler);
            when(operationStrategy.getHandler(operation)).thenReturn(handler);
        }
    }

    @Test
    void processTransactions_ShouldCallCorrectHandlerForEachTransaction() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(Operation.BALANCE, "apple", 10),
                new FruitTransaction(Operation.SUPPLY, "banana", 5),
                new FruitTransaction(Operation.PURCHASE, "orange", 3)
        );

        fruitShopService.processTransactions(transactions);

        for (FruitTransaction transaction : transactions) {
            verify(operationStrategy).getHandler(transaction.getOperation());
            verify(handlers.get(transaction.getOperation())).handleOperation(transaction);
        }
    }
}
