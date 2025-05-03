package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.servicehandler.FruitOperationHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {

    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final int TRANSACTION_QUANTITY = 10;

    private static final String HANDLER_ERROR_MESSAGE = "Handler error";

    private static final FruitTransaction.Operation OPERATION_SUPPLY
            = FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation OPERATION_RETURN
            = FruitTransaction.Operation.RETURN;
    private static final FruitTransaction.Operation OPERATION_PURCHASE
            = FruitTransaction.Operation.PURCHASE;

    private ShopServiceImpl shopService;
    private Map<FruitTransaction.Operation, FruitOperationHandler> operationHandlers;

    @BeforeEach
    public void setUp() {
        operationHandlers = new HashMap<>();
    }

    @Test
    public void testProcess_ValidTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(OPERATION_SUPPLY, FRUIT_APPLE,
                TRANSACTION_QUANTITY);
        FruitOperationHandler handler = transaction1 -> {};
        operationHandlers.put(OPERATION_SUPPLY, handler);

        shopService = new ShopServiceImpl(operationHandlers);
        shopService.process(Collections.singletonList(transaction));
    }

    @Test
    public void testProcess_NoHandlerFound_notOk() {
        FruitTransaction transaction = new FruitTransaction(OPERATION_RETURN,
                FRUIT_BANANA, TRANSACTION_QUANTITY);
        operationHandlers.put(OPERATION_SUPPLY, transaction1 -> {});

        shopService = new ShopServiceImpl(operationHandlers);
        shopService.process(Collections.singletonList(transaction));
    }

    @Test
    public void testProcess_HandlerThrowsException_notOk() {
        FruitTransaction transaction = new FruitTransaction(OPERATION_PURCHASE,
                FRUIT_APPLE, TRANSACTION_QUANTITY);
        FruitOperationHandler handler = transaction1 -> {
            throw new RuntimeException(HANDLER_ERROR_MESSAGE);
        };
        operationHandlers.put(OPERATION_PURCHASE, handler);

        shopService = new ShopServiceImpl(operationHandlers);
        shopService.process(Collections.singletonList(transaction));
    }
}
