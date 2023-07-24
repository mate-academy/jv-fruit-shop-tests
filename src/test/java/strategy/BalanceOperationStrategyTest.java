package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.FruitStorageDao;
import dao.FruitStorageDaoImpl;
import java.util.HashMap;
import java.util.Map;
import model.Fruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationStrategyTest {
    private FruitStorageDao fruitStorageDao;
    private BalanceOperationStrategy balanceOperationStrategy;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        balanceOperationStrategy = new BalanceOperationStrategy();
        fruitStorageDao.set("banana", 15);
        fruitStorageDao.set("apple", 10);
    }

    @Test
    void handleBalanceOperation_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 15);
        expected.put("apple", 5);

        Fruit fruit = new Fruit("apple", 5);
        balanceOperationStrategy.handle(fruit, fruitStorageDao);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "The quantity of existing fruit should remain unchanged after balance operation");
    }
}
