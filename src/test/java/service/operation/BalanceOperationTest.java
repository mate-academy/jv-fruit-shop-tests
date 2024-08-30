package service.operation;

import dao.FruitDaoImpl;
import db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.TestConstants;

class BalanceOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperation(new FruitDaoImpl());
    }

    @Test
    void handle_validData_isOk() {
        String fruitName = TestConstants.BANANA;
        int quantity = 150;
        operationHandler.handle(fruitName, quantity);
        Assertions.assertTrue(Storage.fruitStock.containsKey(fruitName));
        Assertions.assertEquals(quantity, Storage.fruitStock.get(fruitName));
    }

    @Test
    void handle_updateExistingFruit_isOk() {
        String fruitName = TestConstants.BANANA;
        int initialQuantity = 50;
        int expectedQuantity = 150;
        Storage.fruitStock.put(fruitName, initialQuantity);
        operationHandler.handle(fruitName, expectedQuantity);
        int actualQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void handle_negativeQuantity_notOk() {
        String fruitName = TestConstants.BANANA;
        int expectedQuantity = 50;
        Storage.fruitStock.put(fruitName, expectedQuantity);
        operationHandler.handle(fruitName, -10);
        int actualQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertNotEquals(expectedQuantity, actualQuantity);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStock.clear();
    }
}
