package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    void process_singleBalance_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20)
        );
        shopService.process(transactions);
        assertEquals(20, Storage.getFruits().get("banana"));
    }

    @Test
    void process_supplyAfterBalance_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.SUPPLY, "banana", 100)
        );
        shopService.process(transactions);
        assertEquals(120, Storage.getFruits().get("banana"));
    }

    @Test
    void process_fullScenarioFromTask_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.BALANCE, "apple", 100),
                new FruitTransaction(Operation.SUPPLY, "banana", 100),
                new FruitTransaction(Operation.PURCHASE, "banana", 13),
                new FruitTransaction(Operation.RETURN, "apple", 10),
                new FruitTransaction(Operation.PURCHASE, "apple", 20),
                new FruitTransaction(Operation.PURCHASE, "banana", 5),
                new FruitTransaction(Operation.SUPPLY, "banana", 50)
        );
        shopService.process(transactions);
        assertEquals(152, Storage.getFruits().get("banana"));
        assertEquals(90, Storage.getFruits().get("apple"));
    }

    @Test
    void process_purchaseMoreThanAvailable_notOk() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 50)
        );
        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }
}
