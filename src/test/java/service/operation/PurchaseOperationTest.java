package service.operation;

import dao.FruitDaoImpl;
import db.Storage;
import exception.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestConstants;

class PurchaseOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperation(new FruitDaoImpl());
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStock.put(TestConstants.APPLE, 120);
        Storage.fruitStock.put(TestConstants.BANANA, 120);
    }

    @Test
    void handle_validDataPurchase_isOk() {
        String fruitName = TestConstants.APPLE;
        int buyerWantsToPurchase = 50;
        operationHandler.handle(fruitName, buyerWantsToPurchase);
        int expectedFruitQuantity = 70;
        int actualFruitQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertEquals(expectedFruitQuantity, actualFruitQuantity);
    }

    @Test
    void handle_purchaseMoreThanAvailable_notOk() {
        int quantityToPurchase = 200;
        Assertions.assertThrows(ValidationException.class,
                () -> operationHandler.handle(TestConstants.APPLE, quantityToPurchase));
    }

    @Test
    void handle_wrongFruitName_isOk() {
        int quantityToPurchase = 30;
        operationHandler.handle(TestConstants.APPLE, quantityToPurchase);
        int expectedQuantity = 120;
        int actualQuantity = Storage.fruitStock.get(TestConstants.BANANA);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStock.clear();
    }
}
