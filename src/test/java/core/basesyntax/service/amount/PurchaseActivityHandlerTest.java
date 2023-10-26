package core.basesyntax.service.amount;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseActivityHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static PurchaseActivityHandler purchaseActivityHandler;

    @BeforeAll
    static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        purchaseActivityHandler = new PurchaseActivityHandler(fruitTransactionDao);
    }

    @Test
    void purchase_activityHandlerForBanana_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, "banana", 30);
        fruitTransactionDao.addToStorage(fruitTransaction);
        FruitTransaction fruitTransactionPurchase
                = FruitTransaction.of(Operation.PURCHASE, "banana", 10);
        purchaseActivityHandler
                .setAmountOfFruit(fruitTransactionPurchase);
        FruitTransaction expected
                = FruitTransaction.of(Operation.BALANCE, "banana", 20);
        FruitTransaction actual = fruitTransactionDao.getFromStorage(fruitTransaction);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void purchase_activityHandlerForApple_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, "apple", 100);
        fruitTransactionDao.addToStorage(fruitTransaction);
        FruitTransaction fruitTransactionPurchase
                = FruitTransaction.of(Operation.PURCHASE, "apple", 40);
        purchaseActivityHandler
                .setAmountOfFruit(fruitTransactionPurchase);
        FruitTransaction expected
                = FruitTransaction.of(Operation.BALANCE, "apple", 60);
        FruitTransaction actual = fruitTransactionDao.getFromStorage(fruitTransaction);
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactions.clear();
    }
}
