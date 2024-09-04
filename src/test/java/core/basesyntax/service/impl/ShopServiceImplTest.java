package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private ShopServiceImpl shopService;
    private FruitDao fruitDao;
    private final FruitTransaction firstTransaction = new FruitTransaction();
    private final FruitTransaction secondTransaction = new FruitTransaction();

    @BeforeEach
    public void setUp() {
        fruitDao = new InMemoryFruitDao();
        OperationStrategy operationStrategy = new SimpleOperationStrategy();
        shopService = new ShopServiceImpl(operationStrategy, fruitDao);

        firstTransaction.setFruit("Apple");
        firstTransaction.setQuantity(15);
        firstTransaction.setOperation("b");

        secondTransaction.setFruit("Banana");
        secondTransaction.setQuantity(17);
        secondTransaction.setOperation("b");
    }

    @Test
    public void getFruitMap_ValidData_Ok() {
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("Apple", 15);
        expectedMap.put("Banana", 17);

        List<FruitTransaction> transactions = Arrays.asList(firstTransaction, secondTransaction);
        shopService.proceedAll(transactions);

        Map<String, Integer> actualMap = shopService.getFruitMap();
        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void proceedAll_ValidData_Ok() {
        List<FruitTransaction> transactions = Arrays.asList(firstTransaction, secondTransaction);
        shopService.proceedAll(transactions);

        assertEquals(15, fruitDao.getAllFruits().get("Apple"));
        assertEquals(17, fruitDao.getAllFruits().get("Banana"));
    }

    @Test
    public void proceedAll_WithNullTransaction_NotOk() {
        List<FruitTransaction> transactions = Arrays.asList(firstTransaction, null);

        RuntimeException exception = assertThrows(RuntimeException.class, ()
                -> shopService.proceedAll(transactions));

        assertTrue(exception.getMessage().contains("Transaction is null"));
    }

    private static class InMemoryFruitDao implements FruitDao {
        private final Map<String, Integer> fruitStorage = new HashMap<>();

        @Override
        public Integer updateFruitQuantity(String key, Integer value) {
            Integer numberOfFruits = fruitStorage.get(key);

            if (numberOfFruits == null) {
                return fruitStorage.put(key, value);
            }
            return fruitStorage.put(key, numberOfFruits + value);
        }

        @Override
        public Map<String, Integer> getAllFruits() {
            return new HashMap<>(fruitStorage);
        }
    }

    private static class SimpleOperationStrategy implements OperationStrategy {
        @Override
        public OperationHandler choseOperationHandler(FruitTransaction.Operation operation) {
            return new SimpleBalanceHandler();
        }
    }

    private static class SimpleBalanceHandler implements OperationHandler {
        @Override
        public Integer executeOperation(FruitDao fruitDao, FruitTransaction fruitTransaction) {
            return fruitDao.updateFruitQuantity(fruitTransaction.getFruit(),
                    fruitTransaction.getQuantity());
        }
    }
}
