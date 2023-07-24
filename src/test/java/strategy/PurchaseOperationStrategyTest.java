package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dao.FruitStorageDao;
import dao.FruitStorageDaoImpl;
import java.util.HashMap;
import java.util.Map;
import model.Fruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationStrategyTest {
    private FruitStorageDao fruitStorageDao;
    private PurchaseOperationStrategy purchaseOperationStrategy;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        purchaseOperationStrategy = new PurchaseOperationStrategy();
        fruitStorageDao.set("banana", 15);
        fruitStorageDao.set("apple", 10);
    }

    @Test
    void handlePurchaseOperation_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 15);
        expected.put("apple", 3);

        Fruit fruit = new Fruit("apple", 7);
        purchaseOperationStrategy.handle(fruit, fruitStorageDao);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }
}
