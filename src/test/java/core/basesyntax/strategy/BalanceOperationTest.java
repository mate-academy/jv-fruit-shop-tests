package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.ShopTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String FRUIT_NAME = "apple";
    private static final String WRONG_FRUIT_NAME = "coconut";
    private static final int WEIGHT = 10;
    private static final int WRONG_WEIGHT = -7;
    private OperationHandler balance;
    private ShopTransaction validTransaction;
    private ShopTransaction notValidTransaction;

    @BeforeEach
    void setUp() {
        balance = new BalanceOperation();
        validTransaction = new ShopTransaction(OperationType.PURCHASE,
                FRUIT_NAME, WEIGHT);
        notValidTransaction = new ShopTransaction(OperationType.PURCHASE,
                WRONG_FRUIT_NAME, WRONG_WEIGHT);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitShopStorage.clear();
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
    void fruitNameInStorage_Ok() {
        final String actual = validTransaction.getFruitName();
        assertEquals(FRUIT_NAME, actual);
    }

    @Test
    void fruitNameInStorage_NotOk() {
        final String actual = notValidTransaction.getFruitName();
        assertNotEquals(FRUIT_NAME, actual);
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
    void name() {
        Storage.fruitShopStorage.put(FRUIT_NAME, WEIGHT);
        balance.handle(validTransaction);
        final Integer expected = Storage.fruitShopStorage
                .get(validTransaction.getFruitName());
        assertEquals(10, expected);
    }
}
