package service.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import data.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest extends OperationHandlerTest {
    private static final int RETURN_QUANTITY_RESULT =
            DEFAULT_QUANTITY + DEFAULT_QUANTITY;
    private static OperationHandler returnOperation;

    @BeforeAll
    static void createReturnOperation() {
        returnOperation = new ReturnOperation();
    }

    @Test
    void returnHandle_valid_ok() {
        returnOperation.handle(defaultTransaction);
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == RETURN_QUANTITY_RESULT);
    }

    @Test
    void returnHandle_zeroQuantity_ok() {
        returnOperation.handle(zeroQuantityTransaction);
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == DEFAULT_QUANTITY);
    }

    @Test
    void returnHandle_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () ->
                returnOperation.handle(negativeQuantityTransaction));
    }

    @Test
    void returnHandle_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () ->
                returnOperation.handle(nullFruitTransaction));
    }

    @Test
    void returnHandle_noExistFruit_notOk() {
        BalanceOperation balanceOperation = new BalanceOperation();
        balanceOperation.handle(defaultTransaction);
        assertThrows(RuntimeException.class, () ->
                returnOperation.handle(noExistFruitTransaction));
    }

    @Test
    void returnHandle_nullTransaction_notOk() {
        assertThrows(RuntimeException.class, () ->
                returnOperation.handle(null));
    }
}
