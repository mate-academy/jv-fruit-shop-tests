package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.TestFruitStorageDaoImpl;
import core.basesyntax.db.TestStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.operation.BalanceOperation;
import core.basesyntax.service.impl.operation.OperationHandler;
import core.basesyntax.service.impl.operation.PurchaseOperation;
import core.basesyntax.service.impl.operation.ReturnOperation;
import core.basesyntax.service.impl.operation.SupplyOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static TestFruitStorageDaoImpl fruitStorageDao;
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;
    private static OperationStrategy operationStrategy;
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new TestFruitStorageDaoImpl();
        handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitStorageDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(fruitStorageDao),
                FruitTransaction.Operation.RETURN, new ReturnOperation(fruitStorageDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitStorageDao));
        operationStrategy = new OperationStrategyImpl(handlerMap);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        TestStorage.fruits.clear();
    }

    @Test
    void process_validTransaction_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("banana"), 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, new Fruit("banana"), 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, new Fruit("banana"), 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, new Fruit("apple"), 5)
        );
        shopService.process(transactions);
        Map<Fruit, Integer> expected = Map.of(
                new Fruit("banana"), 107,
                new Fruit("apple"), 15
        );
        Map<Fruit, Integer> actual = TestStorage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    void process_nullTransaction_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(null));
    }

    @Test
    void process_emptyTransaction_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> shopService.process(List.of()));
    }
}
