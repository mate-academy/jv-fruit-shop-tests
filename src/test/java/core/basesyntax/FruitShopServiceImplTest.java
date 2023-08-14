package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;

    @BeforeEach
    void setUp() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceOperationHandler());
        operationHandlerMap.put("s", new SupplyOperationHandler());
        operationHandlerMap.put("p", new PurchaseOperationHandler());
        operationHandlerMap.put("r", new ReturnOperationHandler());
        OperationStrategy strategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(strategy);
    }

    @Test
    void process_validCase_Ok() {
        List<FruitTransaction> transactionsList = List.of(
                new FruitTransaction("b", "banana", 120),
                new FruitTransaction("s", "apple", 50),
                new FruitTransaction("p", "apple", 13),
                new FruitTransaction("r", "banana", 10));
        fruitShopService.process(transactionsList);
        Assertions.assertEquals(2, Storage.getMap().size());
        Assertions.assertEquals(Map.of("banana", 130, "apple", 37), Storage.getMap());
    }

    @Test
    void process_nullData_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                fruitShopService.process(null));
    }

    @Test
    void process_emptyList_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                fruitShopService.process(Collections.emptyList()));
    }

    @Test
    void process_negativeValue_notOk() {
        List<FruitTransaction> transactionsList = List.of(
                new FruitTransaction("b", "banana", -1000),
                new FruitTransaction("s", "apple", 50),
                new FruitTransaction("p", "apple", 13),
                new FruitTransaction("r", "banana", 10));
        Assert.assertThrows(RuntimeException.class, () ->
                fruitShopService.process(transactionsList));
    }

    @AfterEach
    void tearDown() {
        Storage.getMap().clear();
    }
}
