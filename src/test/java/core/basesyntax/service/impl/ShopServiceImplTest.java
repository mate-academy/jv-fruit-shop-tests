package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransactionImpl;
import core.basesyntax.operationhandlers.BalanceOperationHandler;
import core.basesyntax.operationhandlers.OperationHandler;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private FruitTransactionImpl transaction;
    private OperationStrategy operationStrategy;
    private OperationHandler balanceHandler;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        transaction = new FruitTransactionImpl();
        balanceHandler = new BalanceOperationHandler();
        Map<FruitTransactionImpl.Operation, OperationHandler> handlers =
                new EnumMap<>(FruitTransactionImpl.Operation.class);
        handlers.put(FruitTransactionImpl.Operation.BALANCE, balanceHandler);
        operationStrategy = new OperationStrategyImpl(handlers);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void clear() {
        Storage.fruits.clear();
    }

    @Test
    void process_ValidBalanceTransaction_ShouldUpdateStorage() {
        transaction.setOperation(FruitTransactionImpl.Operation.BALANCE);
        transaction.setFruit("apple");
        transaction.setQuantity(50);
        shopService.process(List.of(transaction));
        assertEquals(50, Storage.fruits.get("apple"));
    }

    @Test
    void process_UnknownOperation_ShouldThrowException() {
        transaction.setOperation(FruitTransactionImpl.Operation.RETURN);
        transaction.setFruit("apple");
        transaction.setQuantity(50);
        assertThrows(RuntimeException.class, () -> shopService.process(List.of(transaction)));
    }

    @Test
    void process_EmptyTransactionList_ShouldNotModifyStorage() {
        Storage.fruits.put("banana", 20);
        shopService.process(List.of());
        assertEquals(20, Storage.fruits.get("banana"));
    }
}
