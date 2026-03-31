package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static List<FruitTransaction> transactions;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private static OperationStrategy operationStrategy;
    private static Storage storage;
    private static ShopService shopService;

    @BeforeAll
    static void listAndMapFill() {
        transactions = new ArrayList<>();
        operationHandlers = new HashMap<>();

        transactions.add(new FruitTransaction("b", "banana", 100));
        transactions.add(new FruitTransaction("b", "apple", 0));
        transactions.add(new FruitTransaction("s", "banana", 100));
        transactions.add(new FruitTransaction("p", "banana", 13));
        transactions.add(new FruitTransaction("r", "banana", 13));
        transactions.add(new FruitTransaction("p", "apple", 0));

        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        operationStrategy = new OperationStrategyImpl(operationHandlers);
        storage = new Storage();
        shopService = new ShopServiceImpl(operationStrategy, storage);
    }

    @BeforeEach
    void clearStorage() {
        storage.clear();
    }

    @Test
    void processValidData() {
        shopService.process(transactions);
        assertEquals(200, storage.getQuantityByFruit("banana"));
    }

    @Test
    void processEdge() {
        shopService.process(transactions);
        assertEquals(0, storage.getQuantityByFruit("apple"));
    }
}
