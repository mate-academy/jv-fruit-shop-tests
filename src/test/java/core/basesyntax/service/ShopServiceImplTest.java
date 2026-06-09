package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import core.basesyntax.service.strategy.handler.OperationHandler;
import core.basesyntax.service.strategy.handler.impl.BalanceOperation;
import core.basesyntax.service.strategy.handler.impl.PurchaseOperation;
import core.basesyntax.service.strategy.handler.impl.ReturnOperation;
import core.basesyntax.service.strategy.handler.impl.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private OperationStrategy strategy;
    private Storage storage;
    private List<FruitTransaction> transactions;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        strategy = new OperationStrategyImpl(operationHandlers);

        FruitTransaction bananaTransaction = new FruitTransaction();
        bananaTransaction.setQuantity(23);
        bananaTransaction.setFruit("banana");
        bananaTransaction.setOperation("s");

        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setQuantity(25);
        appleTransaction.setFruit("apple");
        appleTransaction.setOperation("s");

        transactions = new ArrayList<>();
        transactions.add(bananaTransaction);
        transactions.add(appleTransaction);

        storage = new Storage();
        storage.put("apple", 100);
        storage.put("banana", 100);
    }

    @Test
    public void validData_Ok() {
        ShopService shopService = new ShopServiceImpl(strategy, storage);
        shopService.process(transactions);

        assertEquals(123, storage.get("banana"));
        assertEquals(125, storage.get("apple"));
    }

    @Test
    public void storage_IsNull_NotOk() {
        ShopService shopService = new ShopServiceImpl(strategy, null);

        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> shopService.process(transactions));
        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void strategy_IsNull_NotOk() {
        ShopService shopService = new ShopServiceImpl(null, storage);

        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> shopService.process(transactions));
        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void transactionsList_IsNull_NotOk() {
        ShopService shopService = new ShopServiceImpl(strategy, storage);

        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> shopService.process(null));
        assertEquals("input data can not be null", exception.getMessage());
    }

    @Test
    public void transactionsList_IsEmpty_NotOk() {
        ShopService shopService = new ShopServiceImpl(strategy, storage);

        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> shopService.process(List.of()));
        assertEquals("transactions list is empty", exception.getMessage());
    }
}
