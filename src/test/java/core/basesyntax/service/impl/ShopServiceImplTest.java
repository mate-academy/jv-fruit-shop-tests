package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    private List<FruitTransaction> transactions;
    private Map<Operation, OperationHandler> mapOfOperations;
    private OperationStrategy operationStrategy;
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        transactions = new ArrayList<>();
        FruitTransaction fruitTransaction = FruitTransaction.of(Operation.B, "banana", 12);
        FruitTransaction fruitTransaction2 = FruitTransaction.of(Operation.B, "orange", 20);
        FruitTransaction fruitTransaction3 = FruitTransaction.of(Operation.B, "kiwi", 35);
        FruitTransaction fruitTransaction4 = FruitTransaction.of(Operation.P, "kiwi", 5);
        FruitTransaction fruitTransaction5 = FruitTransaction.of(Operation.R, "orange", 10);
        FruitTransaction fruitTransaction6 = FruitTransaction.of(Operation.S, "banana", 2);
        FruitTransaction fruitTransaction1 = FruitTransaction.of(Operation.B, "mango", 0);
        transactions.add(fruitTransaction);
        transactions.add(fruitTransaction1);
        transactions.add(fruitTransaction2);
        transactions.add(fruitTransaction3);
        transactions.add(fruitTransaction4);
        transactions.add(fruitTransaction5);
        transactions.add(fruitTransaction6);
        mapOfOperations = new HashMap<>();
        mapOfOperations.put(Operation.P, new PurchaseHandler());
        mapOfOperations.put(Operation.B, new BalanceHandler());
        mapOfOperations.put(Operation.S, new AddOperationHandler());
        mapOfOperations.put(Operation.R, new AddOperationHandler());
        operationStrategy = new OperationStrategyImpl(mapOfOperations);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_returnGoodMap_Ok() {
        Map<String, Integer> result = shopService.process(transactions);
        Assertions.assertEquals(4, result.size());
        Assertions.assertTrue(result.containsKey("banana"));
        Assertions.assertTrue(result.containsKey("orange"));
        Assertions.assertTrue(result.containsKey("kiwi"));
        Assertions.assertTrue(result.containsKey("mango"));
        Assertions.assertEquals(14, result.get("banana"));
        Assertions.assertEquals(30, result.get("orange"));
        Assertions.assertEquals(30, result.get("kiwi"));
        Assertions.assertEquals(0, result.get("mango"));
    }

    @Test
    void process_emptyList_Ok() {
        Map<String, Integer> result = shopService.process(new ArrayList<>());
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void process_listIsNull_notOk() {
        Assertions.assertThrows(NullPointerException.class, () -> shopService.process(null));
    }
}
