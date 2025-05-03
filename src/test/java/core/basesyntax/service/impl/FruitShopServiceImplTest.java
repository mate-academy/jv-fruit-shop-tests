package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int QUANTITY1 = 50;
    private static final int QUANTITY2 = 100;
    private static final int NEW_QUANTITY_1 = 30;
    private static final int NEW_QUANTITY_2 = 40;
    private static final int ZERO = 0;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHalndlerMap =
            new HashMap<>();
    private FruitShopService fruitShopService;
    private Map<String, Integer> expected = new HashMap<>();
    private Map<String, Integer> actual = new HashMap<>();
    private List<FruitTransaction> fruitTransactions;
    private final FruitTransaction fruitTransaction1 = new FruitTransaction(FruitTransaction
            .Operation.BALANCE, BANANA, QUANTITY1);
    private final FruitTransaction fruitTransaction2 = new FruitTransaction(FruitTransaction
            .Operation.BALANCE, APPLE, QUANTITY2);
    private final FruitTransaction fruitTransaction3 = new FruitTransaction(FruitTransaction
            .Operation.SUPPLY, BANANA, NEW_QUANTITY_1);
    private final FruitTransaction fruitTransaction4 = new FruitTransaction(FruitTransaction
            .Operation.SUPPLY, APPLE, NEW_QUANTITY_2);
    private final FruitTransaction fruitTransaction5 = new FruitTransaction(FruitTransaction
            .Operation.PURCHASE, BANANA, QUANTITY1);
    private final FruitTransaction fruitTransaction6 = new FruitTransaction(FruitTransaction
            .Operation.PURCHASE, APPLE, QUANTITY2);

    @Test
    void processOperationWithBalance_Ok() {
        operationHalndlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        fruitTransactions = List.of(fruitTransaction1, fruitTransaction2);
        fruitShopService = new FruitShopServiceImpl(new
                OperationStrategyImpl(operationHalndlerMap));
        expected.put(BANANA, QUANTITY1);
        expected.put(APPLE, QUANTITY2);
        fruitShopService.processOfOperations(fruitTransactions);
        actual = Storage.FRUITS;
        assertEquals(expected, actual);
    }

    @Test
    void processOperationWithSupply_Ok() {
        operationHalndlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        fruitTransactions = List.of(fruitTransaction3, fruitTransaction4);
        fruitShopService = new FruitShopServiceImpl(new
                OperationStrategyImpl(operationHalndlerMap));
        Storage.FRUITS.put(BANANA, QUANTITY1);
        Storage.FRUITS.put(APPLE, QUANTITY2);
        expected.put(BANANA, QUANTITY1 + NEW_QUANTITY_1);
        expected.put(APPLE, QUANTITY2 + NEW_QUANTITY_2);
        fruitShopService.processOfOperations(fruitTransactions);
        actual = Storage.FRUITS;
        assertEquals(expected, actual);
    }

    @Test
    void processOperationWithPurchase_Ok() {
        operationHalndlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        fruitTransactions = List.of(fruitTransaction5, fruitTransaction6);
        fruitShopService = new FruitShopServiceImpl(new
                OperationStrategyImpl(operationHalndlerMap));
        Storage.FRUITS.put(BANANA, QUANTITY1);
        Storage.FRUITS.put(APPLE, QUANTITY2);
        expected.put(BANANA, ZERO);
        expected.put(APPLE, ZERO);
        fruitShopService.processOfOperations(fruitTransactions);
        actual = Storage.FRUITS;
        assertEquals(expected, actual);
    }

    @Test
    void processOperationEmptyListOrNull_notOk() {
        assertThrows(RuntimeException.class, () -> fruitShopService
                .processOfOperations(fruitTransactions));
        fruitTransactions = List.of();
        assertThrows(RuntimeException.class, () -> fruitShopService
                .processOfOperations(fruitTransactions));
    }

    @AfterEach
    void doAfterEachTest() {
        Storage.FRUITS.clear();
    }
}
