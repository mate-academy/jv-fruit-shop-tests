package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void notFoundProduct() {
        Storage.STORAGE.remove("banana");
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));

        assertEquals("Can't find fruit: banana", exception.getMessage());

    }

    @Test
    void notEnoughProduct() {
        Storage.STORAGE.put("banana", 10);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

        List<FruitTransaction> transactions = new ArrayList<>();

        transactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 9999));

        ShopService shopService = new ShopServiceImpl(operationStrategy);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));

        assertEquals("Too little of product: banana", exception.getMessage());

    }
}
