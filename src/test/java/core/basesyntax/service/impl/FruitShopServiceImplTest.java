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
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHalndlerMap =
            Map.of(
                    FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                    FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                    FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                    FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    private FruitShopService fruitShopService = new FruitShopServiceImpl(
            new OperationStrategyImpl(operationHalndlerMap));
    private Map<String, Integer> expected = new HashMap<>();
    private Map<String, Integer> actual = new HashMap<>();
    private List<FruitTransaction> fruitTransactions;
    private final FruitTransaction fruitTransaction1 = new FruitTransaction(FruitTransaction
            .Operation.BALANCE, "banana", 50);
    private final FruitTransaction fruitTransaction2 = new FruitTransaction(FruitTransaction
            .Operation.BALANCE, "apple", 100);
    private final FruitTransaction fruitTransaction3 = new FruitTransaction(FruitTransaction
            .Operation.SUPPLY, "banana", 30);
    private final FruitTransaction fruitTransaction4 = new FruitTransaction(FruitTransaction
            .Operation.SUPPLY, "apple", 40);
    private final FruitTransaction fruitTransaction5 = new FruitTransaction(FruitTransaction
            .Operation.PURCHASE, "banana", 30);
    private final FruitTransaction fruitTransaction6 = new FruitTransaction(FruitTransaction
            .Operation.PURCHASE, "apple", 40);
    private final FruitTransaction fruitTransaction7 = new FruitTransaction(FruitTransaction
            .Operation.RETURN, "banana", 10);
    private final FruitTransaction fruitTransaction8 = new FruitTransaction(FruitTransaction
            .Operation.RETURN, "apple", 10);

    @Test
    void processOperationBanana_Ok() {
        expected.put("banana", 60);
        fruitTransactions = List.of(fruitTransaction1, fruitTransaction3, fruitTransaction5,
                fruitTransaction7);
        fruitShopService.processOfOperations(fruitTransactions);
        actual = Storage.FRUITS;
        assertEquals(expected, actual, "There's an error during processing the operations");
    }

    @Test
    void processOperationApple_Ok() {
        expected.put("apple", 110);
        fruitTransactions = List.of(fruitTransaction2, fruitTransaction4, fruitTransaction6,
                fruitTransaction8);
        fruitShopService.processOfOperations(fruitTransactions);
        actual = Storage.FRUITS;
        assertEquals(expected, actual, "There's an error during processing the operations");
    }

    @Test
    void processOperationBananaApple_Ok() {
        expected.put("banana", 60);
        expected.put("apple", 110);
        fruitTransactions = List.of(fruitTransaction1, fruitTransaction2, fruitTransaction3,
                fruitTransaction4, fruitTransaction5, fruitTransaction6, fruitTransaction7,
                fruitTransaction8);
        fruitShopService.processOfOperations(fruitTransactions);
        actual = Storage.FRUITS;
        assertEquals(expected, actual, "There's an error during processing the operations");
    }

    @Test
    void processOperationEmptyList_notOk() {
        assertThrows(RuntimeException.class, () -> fruitShopService
                .processOfOperations(fruitTransactions));
    }

    @AfterEach
    void doAfterEachTest() {
        Storage.FRUITS.clear();
    }
}
