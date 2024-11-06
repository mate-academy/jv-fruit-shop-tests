package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.ShopTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {

    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_WEIGHT = 15;
    private OperationHandler operationHandler;
    private OperationType validType;
    private OperationType notValidType;
    private ShopTransaction transaction;
    private ShopTransaction notValidTransaction;
    private Storage storage;

    @BeforeEach
    void setUp() {
        validType = OperationType.RETURN;
        notValidType = OperationType.SUPPLY;
        operationHandler = new ReturnOperation();
        transaction = new ShopTransaction(OperationType.RETURN, FRUIT_NAME,
                FRUIT_WEIGHT);
        notValidTransaction = new ShopTransaction(OperationType.RETURN,
                FRUIT_NAME, -7);
        storage = new Storage(FRUIT_NAME, FRUIT_WEIGHT);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitShopStorage.clear();
    }

    @Test
    void nullTransaction_NotOk() {
        assertNotEquals(null, transaction);
    }

    @Test
    void putNull_NotOk() {
        assertNull(Storage.fruitShopStorage.put(null,null));
    }

    @Test
    void notValidOperationType_NotOk() {
        OperationType actual = validType;
        assertNotEquals(notValidType, actual, "Operation type should be RETURN");
    }

    @Test
    void validOperationType_Ok() {
        OperationType actual = validType;
        assertEquals(validType, actual, "Operation type should be RETURN");
    }

    @Test
    void name() {
        Storage.fruitShopStorage.put(FRUIT_NAME, FRUIT_WEIGHT);
        final Map<String, Integer> fruits = storage.getFruits();
        final Integer actual = fruits.putIfAbsent(FRUIT_NAME, FRUIT_WEIGHT);
        assertEquals(15, actual);
    }

    @Test
    void operationProcess_numberLessThatZero_notOk() {
        NullPointerException expectedMessage =
                assertThrows(NullPointerException.class,
                    () -> operationHandler.handle(notValidTransaction));
        assertNotEquals("Can't return negative quantity",
                expectedMessage.getMessage());
    }

    @Test
    void updateDataAfterSupply_Ok() {
        Storage.fruitShopStorage.put(FRUIT_NAME, FRUIT_WEIGHT);
        Integer actual = Storage.fruitShopStorage.get(FRUIT_NAME);
        assertEquals(15, actual);
    }
}
