package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.ShopTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String APPLE_KEY = "apple";
    private static final int SUPPLY_WEIGHT = 12;
    private static OperationHandler chooseOperation;
    private ShopTransaction validTransaction;
    private ShopTransaction notValidTransaction;

    @BeforeAll
    static void beforeAll() {
        chooseOperation = new SupplyOperation();
    }

    @BeforeEach
    void setUp() {
        validTransaction = new ShopTransaction(OperationType.SUPPLY, APPLE_KEY, 10);
        notValidTransaction = new ShopTransaction(OperationType.SUPPLY, "pineapple", -12);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitShopStorage.clear();
    }

    @Test
    void putNull_NotOk() {
        assertNull(Storage.fruitShopStorage.put(null, null));
    }

    @Test
    void notValidOperationType_NotOk() {
        OperationType actual = OperationType.PURCHASE;
        assertNotEquals(OperationType.RETURN,
                actual, "Operation type should be RETURN");
    }

    @Test
    void validOperationType_Ok() {
        OperationType actual = OperationType.PURCHASE;
        assertEquals(OperationType.PURCHASE, actual);
    }

    @Test
    void weightInStorage_Ok() {
        final int actual = validTransaction.getWeight();
        assertEquals(10, actual);
    }

    @Test
    void weightInStorage_NotOK() {
        final int actual = notValidTransaction.getWeight();
        assertNotEquals(10, actual);
    }

    @Test
    void operationWithValidData_Ok() {
        Storage.fruitShopStorage.put(APPLE_KEY, 14);
        chooseOperation.handle(validTransaction);
        assertEquals(24, Storage.fruitShopStorage.get(APPLE_KEY));
    }

    @Test
    void operationWithNotValidData_NotOk() {
        Integer expected = Storage.fruitShopStorage
                .put(notValidTransaction.getFruitName(),
                notValidTransaction.getWeight());
        Integer actual = Storage.fruitShopStorage
                .put(validTransaction.getFruitName(),
                validTransaction.getWeight());
        assertEquals(expected, actual);
    }

    @Test
    void fruitNameInStorage_NotOk() {
        final String actual = notValidTransaction.getFruitName();
        assertNotEquals(APPLE_KEY, actual);
    }

    @Test
    void updateDataAfterSupply_Ok() {
        Storage.fruitShopStorage.put(APPLE_KEY, SUPPLY_WEIGHT);
        Integer actual = Storage.fruitShopStorage.get(APPLE_KEY);
        assertEquals(12, actual);
    }

    @Test
    void checkValidTransaction() {
        Storage.fruitShopStorage.put(APPLE_KEY, 17);
        chooseOperation.handle(validTransaction);
        String fruitAfter = validTransaction.getFruitName();
        int weightAfterOperation = Storage.fruitShopStorage.get(fruitAfter);
        assertEquals(27, weightAfterOperation);
    }

    @Test
    void operationProcess_numberLessThatZero_notOk() {
        NullPointerException expectedMessage =
                assertThrows(NullPointerException.class,
                        () -> chooseOperation.handle(notValidTransaction));
        assertNotEquals("Can't return negative quantity",
                expectedMessage.getMessage());
    }
}
