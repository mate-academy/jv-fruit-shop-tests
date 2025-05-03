package service.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import data.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest extends OperationHandlerTest {

    private static final int SUPPLY_QUANTITY_RESULT =
            DEFAULT_QUANTITY + DEFAULT_QUANTITY;
    private static OperationHandler supplyOperation;

    @BeforeAll
    static void createSupplyOperation() {
        supplyOperation = new SupplyOperation();
    }

    @Test
    void supplyHandle_valid_ok() {
        supplyOperation.handle(defaultTransaction);
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == SUPPLY_QUANTITY_RESULT);
    }

    @Test
    void supplyHandle_noExistFruit_ok() {
        supplyOperation.handle(noExistFruitTransaction);
        assertTrue(Storage.getFruitStorage().containsKey(NO_EXIST_FRUIT)
                && Storage.getFruitStorage().get(NO_EXIST_FRUIT) == DEFAULT_QUANTITY);
    }

    @Test
    void supplyHandle_zeroQuantity_ok() {
        supplyOperation.handle(zeroQuantityTransaction);
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == DEFAULT_QUANTITY);
    }

    @Test
    void supplyHandle_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () ->
                supplyOperation.handle(negativeQuantityTransaction));
    }

    @Test
    void supplyHandle_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () ->
                supplyOperation.handle(nullFruitTransaction));
    }

    @Test
    void supplyHandle_nullTransaction_notOk() {
        assertThrows(RuntimeException.class, () ->
                supplyOperation.handle(null));
    }

    @AfterAll
    static void deleteNoExistFruitFromStorage() {
        Storage.removeFromFruitStorage(NO_EXIST_FRUIT);
    }
}
