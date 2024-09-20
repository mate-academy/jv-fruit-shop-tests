package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService shopService;
    private static OperationStrategy operationStrategy;
    private static FruitTransaction balTransaction;
    private static FruitTransaction purTransaction;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        balTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        purTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        shopService = new ShopServiceImpl(operationStrategy);
        storage = new Storage();
    }

    @Test
    void process_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(balTransaction);
        list.add(purTransaction);
        shopService.process(list, storage);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(80, fruits.get("banana"));
    }
}
