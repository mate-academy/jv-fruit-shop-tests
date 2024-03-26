package core.basesyntax.service.impl;

import core.basesyntax.db.ProductStorage;
import core.basesyntax.dto.ProductTransaction;
import core.basesyntax.enums.Operation;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import core.basesyntax.strategy.handler.impl.PurchaseHandler;
import core.basesyntax.strategy.handler.impl.ReturnHandler;
import core.basesyntax.strategy.handler.impl.SupplyHandler;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionProcessorImplTest {
    private static final int INITIAL_APPLE_QUANTITY = 0;
    private static final int INITIAL_BANANA_QUANTITY = 0;
    private static final int BALANCE_APPLE_QUANTITY = 10;
    private static final int PURCHASE_APPLE_QUANTITY = 2;
    private static final int RETURN_APPLE_QUANTITY = 5;
    private static final int SUPPLY_BANANA_QUANTITY = 20;

    @Test
    public void testProcess() {
        OperationHandler balanceHandler = new BalanceHandler();
        OperationHandler purchaseHandler = new PurchaseHandler();
        OperationHandler returnHandler = new ReturnHandler();
        OperationHandler supplyHandler = new SupplyHandler();
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, balanceHandler);
        operationHandlers.put(Operation.PURCHASE, purchaseHandler);
        operationHandlers.put(Operation.RETURN, returnHandler);
        operationHandlers.put(Operation.SUPPLY, supplyHandler);
        TransactionProcessor transactionProcessor = new TransactionProcessorImpl(operationHandlers);

        List<ProductTransaction> transactions = List.of(
                new ProductTransaction(Operation.BALANCE, "Apple", BALANCE_APPLE_QUANTITY),
                new ProductTransaction(
                        Operation.PURCHASE,
                        "Apple", PURCHASE_APPLE_QUANTITY
                ),
                new ProductTransaction(Operation.RETURN, "Apple", RETURN_APPLE_QUANTITY),
                new ProductTransaction(Operation.SUPPLY, "Banana", SUPPLY_BANANA_QUANTITY)
        );
        transactionProcessor.process(transactions);
        assertEquals(
                INITIAL_APPLE_QUANTITY + BALANCE_APPLE_QUANTITY
                        + RETURN_APPLE_QUANTITY - PURCHASE_APPLE_QUANTITY,
                ProductStorage.STORAGE.getOrDefault("Apple", 0));
        assertEquals(
                INITIAL_BANANA_QUANTITY + SUPPLY_BANANA_QUANTITY,
                ProductStorage.STORAGE.getOrDefault("Banana", 0));
    }
}
