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

class ReturnActivityHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static ReturnActivityHandler returnActivityHandler;

    @BeforeAll
    static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        returnActivityHandler = new ReturnActivityHandler(fruitTransactionDao);
    }

    @Test
    void return_activityHandlerForBanana_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, "banana", 60);
        fruitTransactionDao.addToStorage(fruitTransaction);
        FruitTransaction fruitTransactionReturn
                = FruitTransaction.of(Operation.RETURN, "banana", 30);
        returnActivityHandler
                .setAmountOfFruit(fruitTransactionReturn);
        FruitTransaction expected
                = FruitTransaction.of(Operation.BALANCE, "banana", 90);
        FruitTransaction actual = fruitTransactionDao.getFromStorage(fruitTransaction);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void return_activityHandlerForApple_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, "apple", 100);
        fruitTransactionDao.addToStorage(fruitTransaction);
        FruitTransaction fruitTransactionPurchase
                = FruitTransaction.of(Operation.RETURN, "apple", 40);
        returnActivityHandler
                .setAmountOfFruit(fruitTransactionPurchase);
        FruitTransaction expected
                = FruitTransaction.of(Operation.BALANCE, "apple", 140);
        FruitTransaction actual = fruitTransactionDao.getFromStorage(fruitTransaction);
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactions.clear();
    }
}
