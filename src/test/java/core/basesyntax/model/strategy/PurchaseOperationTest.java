package core.basesyntax.model.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.PurchaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_AMOUNT = 13;
    private static final int PURCHASE_AMOUNT = 4;
    private PurchaseOperation purchaseOperation;
    private FruitDao fruitDao;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
        fruitDao = new FruitDaoImpl();
    }

    @AfterEach
    void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    void purchase_performance_notOk() {
        FruitTransaction fruitTransactionPurchase = new FruitTransaction();
        fruitTransactionPurchase.setName(FRUIT_NAME);
        fruitTransactionPurchase.setType(FruitTransaction.Operation.PURCHASE);
        fruitTransactionPurchase.setAmount(PURCHASE_AMOUNT);
        fruitDao.addFruits(FRUIT_NAME, FRUIT_AMOUNT);
        purchaseOperation.handle(fruitTransactionPurchase);
        assertFalse(fruitDao.get(FRUIT_NAME) < PURCHASE_AMOUNT, "The amount: "
                + PURCHASE_AMOUNT + "can't be sold");
    }

    @Test
    void purchase_performance_Ok() {
        FruitTransaction fruitTransactionPurchase = new FruitTransaction();
        fruitTransactionPurchase.setName(FRUIT_NAME);
        fruitTransactionPurchase.setType(FruitTransaction.Operation.PURCHASE);
        fruitTransactionPurchase.setAmount(PURCHASE_AMOUNT);

        fruitDao.addFruits(FRUIT_NAME, FRUIT_AMOUNT);
        purchaseOperation.handle(fruitTransactionPurchase);
        Integer amountBefore = Storage.storage.get(FRUIT_NAME);
        assertEquals(FRUIT_AMOUNT - PURCHASE_AMOUNT, amountBefore);
    }
}
