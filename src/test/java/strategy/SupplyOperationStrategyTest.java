package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.FruitStorageDao;
import dao.FruitStorageDaoImpl;
import java.util.HashMap;
import java.util.Map;
import model.Fruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationStrategyTest {
    private FruitStorageDao fruitStorageDao;
    private SupplyOperationStrategy supplyOperationStrategy;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        supplyOperationStrategy = new SupplyOperationStrategy();
        fruitStorageDao.set("banana", 15);
        fruitStorageDao.set("apple", 10);
    }

    @Test
    void handleSupplyOperation_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 35);
        expected.put("apple", 10);

        Fruit fruit = new Fruit("banana", 20);
        supplyOperationStrategy.handle(fruit, fruitStorageDao);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }
}
