package service.operation;

import dao.FruitDaoImpl;
import db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestConstants;

class ReturnOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new ReturnOperation(new FruitDaoImpl());
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStock.put(TestConstants.APPLE, 120);
        Storage.fruitStock.put(TestConstants.BANANA, 120);
    }

    @Test
    void handle_validReturn_isOk() {
        String fruitName = TestConstants.BANANA;
        int quantityToReturn = 50;
        operationHandler.handle(fruitName, quantityToReturn);
        int expectedQuantity = 170;
        int actualQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void handle_invalidQuantityReturn_notOk() {
        String fruitName = TestConstants.APPLE;
        int quantityToReturn = 70;
        operationHandler.handle(fruitName, quantityToReturn);
        int expectedFruitQuantity = 70;
        int actualFruitQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertNotEquals(expectedFruitQuantity, actualFruitQuantity);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStock.clear();
    }
}
