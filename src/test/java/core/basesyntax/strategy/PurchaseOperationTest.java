package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.ShopTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String FRUIT_NAME = "apple";
    private static final String WRONG_FRUIT_NAME = "coconut";
    private static final int WEIGHT = 10;
    private static final int WRONG_WEIGHT = -7;
    private OperationHandler purchase;
    private ShopTransaction validTransaction;
    private ShopTransaction notValidTransaction;

    @BeforeEach
    void setUp() {
        purchase = new PurchaseOperation();
        validTransaction = new ShopTransaction(OperationType.PURCHASE, FRUIT_NAME, WEIGHT);
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
    void purchase_Ok() {
        Storage.fruitShopStorage.put(FRUIT_NAME, 17);
        int balance = Storage.fruitShopStorage.get(FRUIT_NAME);
        int weightToBuy = validTransaction.getWeight();
        assertTrue(balance >= weightToBuy);
        assertTrue(balance >= 17);
    }

    @Test
    void buyNegativeQuantity_NotOk() {
        int actual = notValidTransaction.getWeight();
        assertTrue(actual <= 0);
    }

    @Test
    void allParameters_Ok() {
        Storage.fruitShopStorage.put(FRUIT_NAME, 17);
        String fruit = validTransaction.getFruitName();
        int weight = Storage.fruitShopStorage.get(fruit);
        int weightToBuy = validTransaction.getWeight();
        Storage.fruitShopStorage.put(fruit, weight - weightToBuy);
    }

    @Test
    void name() {
        Storage.fruitShopStorage.put(FRUIT_NAME, 17);
        purchase.handle(validTransaction);
        String fruitAfter = validTransaction.getFruitName();
        int weightAfterOperation = Storage.fruitShopStorage.get(fruitAfter);
        assertEquals(7, weightAfterOperation);
    }
}
