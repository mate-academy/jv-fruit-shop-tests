package core.basesyntax.service;

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
    private FruitOperationDao fruitOperationDao;
    private OperationStrategy strategy;
    private ShopServiceImpl shopService;

    @BeforeEach
    void setUp() {
        // Простий in-memory DAO
        fruitOperationDao = new InMemoryFruitOperationDao();

        // Стратегія для операцій (тільки PURCHASE і SUPPLY для тесту)
        strategy = new SimpleOperationStrategy();

        shopService = new ShopServiceImpl(fruitOperationDao, strategy);
    }

    @Test
    void changeQuantityStore_balance_addsFruit() {
        FruitOperation balanceOp = new FruitOperation(FruitOperation.Operation.BALANCE,
                "apple", 50);

        shopService.changeQuantityStore(List.of(balanceOp));

        Optional<FruitOperation> result = fruitOperationDao.get("apple");
        assertTrue(result.isPresent());
        assertEquals(50, result.get().getQuantity());
    }

    @Test
    void changeQuantityStore_purchase_reducesQuantity() {
        fruitOperationDao.add(new FruitOperation(FruitOperation.Operation.BALANCE,
                "apple", 100));

        FruitOperation purchaseOp = new FruitOperation(FruitOperation.Operation.PURCHASE,
                "apple", 30);
        shopService.changeQuantityStore(List.of(purchaseOp));

        Optional<FruitOperation> result = fruitOperationDao.get("apple");
        assertTrue(result.isPresent());
        assertEquals(70, result.get().getQuantity());
    }

    @Test
    void changeQuantityStore_supply_increasesQuantity() {
        fruitOperationDao.add(new FruitOperation(FruitOperation.Operation.BALANCE,
                "banana", 20));

        FruitOperation supplyOp = new FruitOperation(FruitOperation.Operation.SUPPLY,
                "banana", 15);
        shopService.changeQuantityStore(List.of(supplyOp));

        Optional<FruitOperation> result = fruitOperationDao.get("banana");
        assertTrue(result.isPresent());
        assertEquals(35, result.get().getQuantity());
    }

    @Test
    void changeQuantityStore_nonExistingFruit_skipsUpdate() {
        FruitOperation purchaseOp = new FruitOperation(FruitOperation.Operation.PURCHASE,
                "orange", 10);

        shopService.changeQuantityStore(List.of(purchaseOp));

        Optional<FruitOperation> result = fruitOperationDao.get("orange");
        assertTrue(result.isEmpty());
    }

    static class InMemoryFruitOperationDao implements FruitOperationDao {
        private final Map<String, FruitOperation> store = new HashMap<>();

        @Override
        public void add(FruitOperation fruit) {
            store.put(fruit.getFruit(), new FruitOperation(fruit.getOperation(),
                    fruit.getFruit(), fruit.getQuantity()));
        }

        @Override
        public Optional<FruitOperation> get(String fruitName) {
            return Optional.ofNullable(store.get(fruitName));
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
                case PURCHASE -> (oldQty, change) -> oldQty - change;
                case SUPPLY, RETURN -> (oldQty, change) -> oldQty + change;
                default -> throw new IllegalArgumentException("Unsupported operation");
            };
        }
    }
}
