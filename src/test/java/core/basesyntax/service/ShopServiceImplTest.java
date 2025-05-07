package core.basesyntax.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitOperationDao;
import core.basesyntax.model.FruitOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private InMemoryFruitOperationDao fruitOperationDao;
    private SimpleOperationStrategy strategy;
    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        fruitOperationDao = new InMemoryFruitOperationDao();
        strategy = new SimpleOperationStrategy();
        shopService = new ShopServiceImpl(fruitOperationDao, strategy);
    }

    @Test
    void testChangeQuantityStoreWithBalanceOperation() {
        FruitOperation balanceOp = new FruitOperation(FruitOperation.Operation.BALANCE,
                "apple", 100);

        shopService.changeQuantityStore(List.of(balanceOp));

        Optional<FruitOperation> stored = fruitOperationDao.get("apple");
        assertTrue(stored.isPresent());
        assertEquals(100, stored.get().getQuantity());
    }

    @Test
    void testChangeQuantityStoreWithSupplyOperation() {
        // Add initial balance
        fruitOperationDao.add(new FruitOperation(FruitOperation.Operation.BALANCE, "orange", 30));

        FruitOperation supplyOp = new FruitOperation(FruitOperation.Operation.SUPPLY, "orange", 20);

        shopService.changeQuantityStore(List.of(supplyOp));

        Optional<FruitOperation> updated = fruitOperationDao.get("orange");
        assertTrue(updated.isPresent());
        assertEquals(50, updated.get().getQuantity());
    }

    @Test
    void testChangeQuantityStoreWithPurchaseOperation() {
        fruitOperationDao.add(new FruitOperation(FruitOperation.Operation.BALANCE,
                "banana", 50));

        FruitOperation purchaseOp = new FruitOperation(FruitOperation.Operation.PURCHASE,
                "banana", 10);

        shopService.changeQuantityStore(List.of(purchaseOp));

        Optional<FruitOperation> updated = fruitOperationDao.get("banana");
        assertTrue(updated.isPresent());
        assertEquals(40, updated.get().getQuantity());
    }

    @Test
    void testChangeQuantityStoreWithNonExistingFruit() {
        FruitOperation purchaseOp = new FruitOperation(FruitOperation.Operation.PURCHASE,
                "mango", 10);

        shopService.changeQuantityStore(List.of(purchaseOp));

        Optional<FruitOperation> result = fruitOperationDao.get("mango");
        assertFalse(result.isPresent());
    }

    @Test
    void testChangeQuantityStoreWithMultipleOperations() {
        fruitOperationDao.add(new FruitOperation(FruitOperation.Operation.BALANCE,
                "kiwi", 10));

        FruitOperation supplyOp = new FruitOperation(FruitOperation.Operation.SUPPLY,
                "kiwi", 5);
        FruitOperation purchaseOp = new FruitOperation(FruitOperation.Operation.PURCHASE,
                "kiwi", 3);

        shopService.changeQuantityStore(List.of(supplyOp, purchaseOp));

        Optional<FruitOperation> updated = fruitOperationDao.get("kiwi");
        assertTrue(updated.isPresent());
        assertEquals(12, updated.get().getQuantity());
    }

    static class InMemoryFruitOperationDao implements FruitOperationDao {
        private final Map<String, FruitOperation> store = new HashMap<>();

        @Override
        public void add(FruitOperation fruit) {
            store.put(fruit.getFruit(), new FruitOperation(
                    fruit.getOperation(),
                    fruit.getFruit(),
                    fruit.getQuantity()
            ));
        }

        @Override
        public Optional<FruitOperation> get(String fruit) {
            return Optional.ofNullable(store.get(fruit));
        }

        @Override
        public void update(FruitOperation fruit) {
            store.put(fruit.getFruit(), fruit);
        }
    }

    static class SimpleOperationStrategy implements OperationStrategy {
        @Override
        public OperationHandler get(FruitOperation.Operation operation) {
            return switch (operation) {
                case SUPPLY -> (current, change) -> current + change;
                case PURCHASE -> (current, change) -> current - change;
                case RETURN -> (current, change) -> current + change;
                default -> (current, change) -> current;
            };
        }
    }
}
