package service.operation;

import dao.FruitDao;
import db.Storage;
import exception.ValidationException;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperation(new FruitDao() {
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

    @BeforeEach
    void setUp() {
        Storage.fruitStock.put("apple", 120);
        Storage.fruitStock.put("banana", 120);
    }

    @Test
    void handle_validDataPurchase_isOk() {
        String fruitName = "apple";
        int buyerWantsToPurchase = 50;
        operationHandler.handle(fruitName, buyerWantsToPurchase);
        int expectedFruitQuantity = 70;
        int actualFruitQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertEquals(expectedFruitQuantity, actualFruitQuantity);
    }

    @Test
    void handle_purchaseMoreThanAvailable_notOk() {
        String fruitName = "apple";
        int quantityToPurchase = 200;
        Assertions.assertThrows(ValidationException.class,
                () -> operationHandler.handle(fruitName, quantityToPurchase));
    }

    @Test
    void handle_wrongFruitName_isOk() {
        String validFruitName = "banana";
        int quantityToPurchase = 30;
        operationHandler.handle("apple", quantityToPurchase);
        int expectedQuantity = 120;
        int actualQuantity = Storage.fruitStock.get(validFruitName);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStock.clear();
    }
}
