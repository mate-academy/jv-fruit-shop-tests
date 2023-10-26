package core.basesyntax.service.amount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceActivityHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static BalanceActivityHandler balanceActivityHandler;

    @BeforeAll
    static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        balanceActivityHandler = new BalanceActivityHandler(fruitTransactionDao);
    }

    @Test
    void balance_activityHandlerForBanana_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, "banana", 50);
        balanceActivityHandler
                .setAmountOfFruit(fruitTransaction);
        FruitTransaction expected = fruitTransaction;
        FruitTransaction actual = fruitTransactionDao.getFromStorage(fruitTransaction);
        assertEquals(expected, actual);
    }

    @Test
    void balance_activityHandlerForApple_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, "apple", 100);
        balanceActivityHandler.setAmountOfFruit(fruitTransaction);
        FruitTransaction expected = fruitTransaction;
        FruitTransaction actual = fruitTransactionDao.getFromStorage(fruitTransaction);
        assertEquals(expected, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactions.clear();
    }
}
