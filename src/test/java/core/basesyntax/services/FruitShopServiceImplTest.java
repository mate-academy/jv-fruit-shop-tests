package core.basesyntax.services;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import core.basesyntax.strategy.handler.impl.OperationStrategyImpl;
import core.basesyntax.strategy.handler.impl.PurchaseHandler;
import core.basesyntax.strategy.handler.impl.ReturnHandler;
import core.basesyntax.strategy.handler.impl.SupplyHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;

    @BeforeEach
    public void setUp() {
        initFruitShopService();
        initStorage();
    }

    private void initFruitShopService() {
        Map<Operation, OperationHandler> operationHandlerMap
                = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandler());
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
    }

    private void initStorage() {
        Storage.storage.clear();
        Storage.storage.put("pineapple", 10);
        Storage.storage.put("banana", 20);
        Storage.storage.put("orange", 30);
    }

    @Test
    public void test_ProcessData_UpdateStorage_Ok() {
        List<FruitTransaction> transactionList = Arrays.asList(
                new FruitTransaction(Operation.BALANCE, "orange", 100),
                new FruitTransaction(Operation.PURCHASE, "orange", 5),
                new FruitTransaction(Operation.RETURN, "orange", 10),
                new FruitTransaction(Operation.SUPPLY, "orange", 15)
        );
        fruitShopService.processData(transactionList);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 20);
        expectedStorage.put("orange", 120);
        expectedStorage.put("pineapple", 10);
        Assertions.assertEquals(expectedStorage, Storage.storage);
    }
}
