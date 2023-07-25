package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void handlePurchaseGreaterThanStored_notOk() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 15);
        expected.put("apple", 10);

        Fruit fruit = new Fruit("apple", 25);
        assertThrows(RuntimeException.class,
                () -> purchaseOperationStrategy.handle(fruit, fruitStorageDao),
                "Can't buy more fruits than stored in stock");

        Map<String,Integer> actual = fruitStorageDao.getFruits();
        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }
}
