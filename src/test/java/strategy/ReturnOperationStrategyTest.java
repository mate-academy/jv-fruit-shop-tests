package strategy;

import dao.FruitStorageDao;
import dao.FruitStorageDaoImpl;
import model.Fruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReturnOperationStrategyTest {
    private FruitStorageDao fruitStorageDao;
    private ReturnOperationStrategy returnOperationStrategy;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        returnOperationStrategy = new ReturnOperationStrategy();
        fruitStorageDao.set("banana", 15);
        fruitStorageDao.set("apple", 10);
    }

    @Test
    void handleReturnOperation_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 15);
        expected.put("apple", 17);

        Fruit fruit = new Fruit("apple", 7);
        returnOperationStrategy.handle(fruit, fruitStorageDao);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }
}