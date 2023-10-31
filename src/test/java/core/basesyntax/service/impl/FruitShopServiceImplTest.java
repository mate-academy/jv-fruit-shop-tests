package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseHandler;
import core.basesyntax.strategy.impl.ReturnHandler;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;
    private List<FruitTransaction> testTransactions;
    private Map<String, Integer> expectedResultMap;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());

        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        testTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("banana"), 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, new Fruit("banana"), 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, new Fruit("apple"), 50),
                new FruitTransaction(FruitTransaction.Operation.RETURN, new Fruit("banana"), 10),
                new FruitTransaction(FruitTransaction.Operation.RETURN, new Fruit("apple"), 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, new Fruit("banana"), 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, new Fruit("apple"), 10)
        );
        expectedResultMap = new HashMap<>();
        expectedResultMap.put("banana", 100);
        expectedResultMap.put("apple", 70);
    }

    @Test
    void processTransactions_isProcessingData_Ok() {
        assertEquals(fruitShopService.processTransactions(testTransactions), expectedResultMap);
    }
}
