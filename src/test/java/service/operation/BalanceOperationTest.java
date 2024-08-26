package service.operation;

import dao.FruitDao;
import db.Storage;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperation(new FruitDao() {
            @Override
            public Integer getBalance(String fruit) {
                return Storage.fruitStock.get(fruit);
            }

            @Override
            public boolean addBalance(String fruit, int quantity) {
                Storage.fruitStock.put(fruit, quantity);
                return Storage.fruitStock.containsKey(fruit)
                        && Storage.fruitStock.get(fruit) == quantity;
            }

            @Override
            public void updateBalance(String fruit, int quantity) {
                Storage.fruitStock.put(fruit, quantity);
            }

            @Override
            public Set<Map.Entry<String, Integer>> getAllEntries() {
                return Storage.fruitStock.entrySet();
            }
        });
    }

    @Test
    void handle_validData_isOk() {
        String fruitName = "banana";
        int quantity = 150;
        operationHandler.handle(fruitName, quantity);
        Assertions.assertTrue(Storage.fruitStock.containsKey(fruitName));
        Assertions.assertEquals(quantity, Storage.fruitStock.get(fruitName));
    }

    @Test
    void handle_updateExistingFruit_isOk() {
        String fruitName = "banana";
        int initialQuantity = 50;
        int expectedQuantity = 150;
        Storage.fruitStock.put(fruitName, initialQuantity);
        operationHandler.handle(fruitName, expectedQuantity);
        int actualQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void handle_invalidQuantity_notOk() {
        String fruitName = "banana";
        int expectedQuantity = 50;
        Storage.fruitStock.put(fruitName, expectedQuantity);
        operationHandler.handle(fruitName, -10);
        int actualQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertNotEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void handle_wrongFruitName_notOk() {
        String expectedFruitName = "banana";
        int expectedFruitQuantity = 150;
        operationHandler.handle("apple", expectedFruitQuantity);
        String actualFruitName = Storage.fruitStock.keySet().stream()
                .filter(k -> k.equals("apple"))
                .findFirst()
                .get();
        int actualFruitQuantity = Storage.fruitStock.get(actualFruitName);
        Assertions.assertEquals(expectedFruitQuantity, actualFruitQuantity);
        Assertions.assertNotEquals(expectedFruitName, actualFruitName);

    }

    @AfterEach
    void tearDown() {
        Storage.fruitStock.clear();
    }
}
