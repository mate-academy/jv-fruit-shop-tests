package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static FruitShopServiceImpl fruitShopService;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceHandler()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.storageMap.clear();
    }

    @DisplayName("Checking for passing null as fruitTransactions value")
    @Test
    void process_nullList_notOk() {
        assertThrows(NullPointerException.class, () ->
                fruitShopService.process(null));
    }

    @DisplayName("Checking for passing empty as fruitTransactions list")
    @Test
    void process_emptyList_ok() {
        List<FruitTransaction> fruitTransactions = Collections.emptyList();
        fruitShopService.process(fruitTransactions);
        assertTrue(Storage.storageMap.isEmpty());
    }

    @DisplayName("Checking for processing list with single fruit transaction")
    @Test
    void process_singleElementInFruitTransactionList_ok() {
        String fruit = "banana";
        int quantity = 10;
        List<FruitTransaction> fruitTransactions = List.of(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, fruit, quantity));
        fruitShopService.process(fruitTransactions);
        assertEquals(quantity, (int) Storage.storageMap.get(fruit));
    }

    @DisplayName("Checking for processing list with multiple fruits transactions")
    @Test
    void process_multipleFruitsInFruitTransactionList_ok() {
        String fruit = "banana";
        int quantity1 = 10;
        int quantity2 = 10;
        int quantity3 = 10;
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, quantity1),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, quantity2),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, quantity3)
        );
        fruitShopService.process(fruitTransactions);
        assertEquals(quantity3, (int) Storage.storageMap.get(fruit));
    }
}
