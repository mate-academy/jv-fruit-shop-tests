package service.operation;

import dao.FruitDaoImpl;
import db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestConstants;

class SupplyOperationTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyOperation(new FruitDaoImpl());
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStock.put(TestConstants.APPLE, 120);
        Storage.fruitStock.put(TestConstants.BANANA, 120);
    }

    @Test
    void handle_validSupply_isOk() {
        String fruitName = TestConstants.BANANA;
        int expectedSupply = 50;
        operationHandler.handle(fruitName, expectedSupply);
        int expectedQuantity = 170;
        int actualQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void handle_wrongQuantity_notOk() {
        String fruitName = TestConstants.APPLE;
        int expectedSupply = 70;
        operationHandler.handle(fruitName, expectedSupply);
        int expectedFruitQuantity = 70;
        int actualFruitQuantity = Storage.fruitStock.get(fruitName);
        Assertions.assertNotEquals(expectedFruitQuantity, actualFruitQuantity);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStock.clear();
    }
}
