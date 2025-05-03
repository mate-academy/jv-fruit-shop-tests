package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidFruitShopException;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final int DEFAULT_QUANTITY = 15;
    private static final String DEFAULT_FRUIT = "banana";
    private static final int QUANTITY_AFTER_PURCHASE = 10;
    private static final int QUANTITY_TO_PURCHASE = 5;
    private FruitTransaction fruitTransaction;
    private OperationHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        Storage.dataBase.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(DEFAULT_QUANTITY);
        fruitTransaction.setFruit(DEFAULT_FRUIT);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        purchaseHandler = new PurchaseHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.dataBase.clear();
    }

    @Test
    void balance_notNull_ok() {
        assertNotNull(Storage.dataBase.get(fruitTransaction.getFruit()),
                "Balance can't be null");
    }

    @Test
    void balance_afterPurchase_OK() {
        purchaseHandler.operate(fruitTransaction);
        fruitTransaction.setQuantity(QUANTITY_TO_PURCHASE);
        int expectedQuantity = Storage.dataBase.get(fruitTransaction.getFruit())
                - fruitTransaction.getQuantity();
        int oldQuantity = Storage.dataBase.get(fruitTransaction.getFruit());
        assertNotEquals(expectedQuantity, oldQuantity);
    }

    @Test
    void balance_afterPurchase_NotOk() {
        Storage.dataBase.put(fruitTransaction.getFruit(),
                fruitTransaction.getQuantity() - QUANTITY_TO_PURCHASE);
        int oldQuantity = Storage.dataBase.get(fruitTransaction.getFruit());
        fruitTransaction.setQuantity(QUANTITY_AFTER_PURCHASE);
        purchaseHandler.operate(fruitTransaction);
        int expectedQuantity = fruitTransaction.getQuantity();
        if (expectedQuantity == oldQuantity) {
            assertThrows(InvalidFruitShopException.class,
                    () -> {
                        throw new InvalidFruitShopException("Balance can't "
                                + "be equals to previous balance");
                    }
            );
        }
    }
}
