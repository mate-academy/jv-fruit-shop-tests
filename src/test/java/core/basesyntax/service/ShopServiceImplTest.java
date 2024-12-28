package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.handlers.BalanceOperation;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> handlersMap;
    private static OperationHandlerStrategy strategy;
    private static ShopService shopService;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeAll
    static void beforeAll() {
        handlersMap = new HashMap<>();
        handlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        strategy = new OperationHandlerStrategyImpl(handlersMap);
        shopService = new ShopServiceImpl(strategy);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(20);

        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(transaction);
    }

    @Test
    void process_validInputFile_Ok() {
        shopService.process(fruitTransactions);
        int expected = 20;
        int actual = FruitStorage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void process_emptyInputFile_Ok() {
        shopService.process(new ArrayList<>());
        assertTrue(FruitStorage.fruits.isEmpty());
    }
}
