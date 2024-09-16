package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class FruitServiceImplTest {
    private final FruitService fruitService = fruitServiceInitialise();

    @Test
    public void processData_emptyFruitTransactions_ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitService.processData(fruitTransactions);
        Map<String, Integer> expected = new HashMap<>();
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processData_normalFruitTransactions_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5));

        fruitService.processData(fruitTransactions);
        Map<String, Integer> expected = Map.of("banana", 115, "apple", 110);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
        Storage.fruits.clear();
    }

    private FruitService fruitServiceInitialise() {
        Map<FruitTransaction.Operation, OperationHandler> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        activityHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        activityHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        activityHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(activityHandlerMap);
        return new FruitServiceImpl(operationStrategy);
    }
}
