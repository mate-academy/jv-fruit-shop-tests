package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationModelTest {

    @Test
    void testGetOperation() {
        OperationModel operationModel = new OperationModel(OperationModel.Operation.PURCHASE,
                FruitModel.APPLE, 26);
        assertEquals(OperationModel.Operation.PURCHASE, operationModel.getOperation());
    }

    @Test
    void testSetOperation() {
        OperationModel operationModel = new OperationModel(OperationModel.Operation.BALANCE,
                FruitModel.LIME, 15);
        operationModel.setOperation(OperationModel.Operation.SUPPLY);
        assertEquals(OperationModel.Operation.SUPPLY, operationModel.getOperation());
    }

    @Test
    void testGetFruit() {
        OperationModel operationModel = new OperationModel(OperationModel.Operation.RETURN,
                FruitModel.BANANA, 19);
        assertEquals(FruitModel.BANANA, operationModel.getFruit());
    }

    @Test
    void testSetFruit() {
        OperationModel operationModel = new OperationModel(OperationModel.Operation.SUPPLY,
                FruitModel.APPLE, 18);
        operationModel.setFruit(FruitModel.APPLE);
        assertEquals(FruitModel.APPLE, operationModel.getFruit());
    }

    @Test
    void testGetAmount() {
        OperationModel operationModel = new OperationModel(OperationModel.Operation.BALANCE,
                FruitModel.APPLE, 53);
        assertEquals(53, operationModel.getAmount());
    }

    @Test
    void testSetAmount() {
        OperationModel operationModel = new OperationModel(OperationModel.Operation.BALANCE,
                FruitModel.APPLE, 78);
        operationModel.setQuantity(52);
        assertEquals(52, operationModel.getAmount());
    }

    @Test
    void testGetOperationFromCodeValid() {
        assertEquals(OperationModel.Operation.SUPPLY,
                OperationModel.Operation.getOperationFromCode("s"));
    }

    @Test
    void testGetOperationFromCodeInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            OperationModel.Operation.getOperationFromCode("x");
        });
        assertEquals("Invalid operation type x", exception.getMessage());
    }
}
