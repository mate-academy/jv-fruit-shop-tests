package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.exception.InsufficientQuantityException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new StorageImpl();
        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy, storage);
    }

    @Test
    void process_ShouldHandleBalanceOperation() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "banana", 20);
        shopService.process(List.of(transaction));

        assertEquals(20, storage.getQuantity("banana"));
    }

    @Test
    void process_ShouldHandleSupplyOperation() {
        storage.addEntry("banana", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 15);
        shopService.process(List.of(transaction));

        assertEquals(25, storage.getQuantity("banana"));
    }

    @Test
    void process_ShouldHandlePurchaseOperation() {
        storage.addEntry("banana", 20);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 5);
        shopService.process(List.of(transaction));

        assertEquals(15, storage.getQuantity("banana"));
    }

    @Test
    void process_ShouldHandleReturnOperation() {
        storage.addEntry("banana", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "banana", 5);
        shopService.process(List.of(transaction));

        assertEquals(15, storage.getQuantity("banana"));
    }

    @Test
    void process_ShouldThrowExceptionForInsufficientQuantity() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 5);

        assertThrows(InsufficientQuantityException.class,
                () -> shopService.process(List.of(transaction)));
    }
}
