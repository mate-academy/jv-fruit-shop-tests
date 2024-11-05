package core.basesyntax;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

class ShopServiceTest {
    private FruitStorage fruitStorage;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();

        OperationHandler balanceHandler = new BalanceOperationHandler(fruitStorage);
        OperationHandler purchaseHandler = new PurchaseOperationHandler(fruitStorage);
        OperationHandler returnHandler = new ReturnOperationHandler(fruitStorage);
        OperationHandler supplyHandler = new SupplyOperationHandler(fruitStorage);

        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, balanceHandler);
        operationHandlerMap.put(Operation.PURCHASE, purchaseHandler);
        operationHandlerMap.put(Operation.RETURN, returnHandler);
        operationHandlerMap.put(Operation.SUPPLY, supplyHandler);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_SupplyTransactions_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "apple", 50)
        );
        shopService.process(transactions);
        assertEquals(150, fruitStorage.getFruitQuantity("apple"));
    }

    @Test
    void process_PurchaseTransaction_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.PURCHASE, "apple", 50)
        );
        shopService.process(transactions);
        assertEquals(50, fruitStorage.getFruitQuantity("apple"));
    }

    @Test
    void process_ReturnTransaction_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "apple", 50),
                new FruitTransaction(Operation.RETURN, "apple", 20)
        );
        shopService.process(transactions);
        assertEquals(70, fruitStorage.getFruitQuantity("apple"));
    }

    @Test
    void process_withInsufficientStock_NotOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "pear", 10),
                new FruitTransaction(Operation.PURCHASE, "pear", 20)
        );

        assertThrows(IllegalArgumentException.class, () -> shopService.process(transactions));
    }
}