package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandler = new HashMap<>();
    private final OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandler);
    private final ShopService shopService = new ShopServiceImpl(operationStrategy);
    private final Map<String, Integer> originalStorage = new HashMap<>(Storage.storage);

    @BeforeEach
    void setup() {
        operationHandler.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandler.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandler.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandler.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
    }

    @Test
    void getOperationPurchase_isOk() {
        Storage.storage.put("apple", 15);
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10));
        shopService.process(fruitTransactionList);
        int expect = 5;
        assertEquals(expect, Storage.storage.get("apple"));
    }

    @Test
    void getOperationReturn_isOk() {
        Storage.storage.put("apple", 15);
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));
        shopService.process(fruitTransactionList);
        int expect = 25;
        assertEquals(expect, Storage.storage.get("apple"));
    }

    @Test
    void getOperationSupply_isOk() {
        Storage.storage.put("apple", 15);
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10));
        shopService.process(fruitTransactionList);
        int expect = 25;
        assertEquals(expect, Storage.storage.get("apple"));
    }

    @Test
    void getOperationBalance_isOk() {
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10));
        shopService.process(fruitTransactionList);
        assertTrue(Storage.storage.containsKey("apple"));
    }

    @AfterEach
    void clean() {
        Storage.storage.clear();
        Storage.storage.putAll(originalStorage);

    }
}
