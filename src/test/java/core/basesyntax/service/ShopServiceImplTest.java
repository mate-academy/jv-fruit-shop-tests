package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.servicehandler.FruitOperationHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {

    private ShopServiceImpl shopService;
    private Map<FruitTransaction.Operation, FruitOperationHandler> operationHandlers;

    @BeforeEach
    public void setUp() {
        operationHandlers = new HashMap<>();
    }

    @Test
    public void testProcess_ValidTransaction() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", 10);
        FruitOperationHandler handler = transaction1 -> {};
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, handler);

        shopService = new ShopServiceImpl(operationHandlers);
        shopService.process(Collections.singletonList(transaction));
    }

    @Test
    public void testProcess_NoHandlerFound() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 5);
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, transaction1 -> {});

        shopService = new ShopServiceImpl(operationHandlers);
        shopService.process(Collections.singletonList(transaction));
    }

    @Test
    public void testProcess_HandlerThrowsException() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 10);
        FruitOperationHandler handler = transaction1 -> {
            throw new RuntimeException("Handler error");
        };
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, handler);

        shopService = new ShopServiceImpl(operationHandlers);
        shopService.process(Collections.singletonList(transaction));
    }
}
