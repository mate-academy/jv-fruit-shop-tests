package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService shopService;
    private static OperationStrategy operationStrategy;
    private static FruitTransaction balTransaction;
    private static FruitTransaction purTransaction;
    private static FruitTransaction supTransaction;
    private static FruitTransaction retTransaction;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
        balTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        purTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        supTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 20);
        retTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void process_Balance_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(balTransaction);
        shopService.process(list, storage);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(100, fruits.get("banana"));
    }

    @Test
    void process_Purchase_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(balTransaction);
        list.add(purTransaction);
        shopService.process(list, storage);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(80, fruits.get("banana"));
    }

    @Test
    void process_Supply_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(balTransaction);
        list.add(supTransaction);
        shopService.process(list, storage);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(120, fruits.get("banana"));
    }

    @Test
    void process_Return_Ok() {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(balTransaction);
        list.add(purTransaction);
        list.add(retTransaction);
        shopService.process(list, storage);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(90, fruits.get("banana"));
    }
}
