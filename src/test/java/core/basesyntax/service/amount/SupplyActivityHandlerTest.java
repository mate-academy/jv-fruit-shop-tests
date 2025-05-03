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

class SupplyActivityHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static SupplyActivityHandler supplyActivityHandler;

    @BeforeAll
    static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        supplyActivityHandler = new SupplyActivityHandler(fruitTransactionDao);
    }

    @Test
    void supply_activityHandlerForBanana_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, "banana", 65);
        fruitTransactionDao.addToStorage(fruitTransaction);
        FruitTransaction fruitTransactionSupply
                = FruitTransaction.of(Operation.SUPPLY, "banana", 35);
        supplyActivityHandler
                .setAmountOfFruit(fruitTransactionSupply);
        FruitTransaction expected
                = FruitTransaction.of(Operation.BALANCE, "banana", 100);
        FruitTransaction actual = fruitTransactionDao.getFromStorage(fruitTransaction);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void supply_activityHandlerForApple_isOk() {
        FruitTransaction fruitTransaction
                = FruitTransaction.of(Operation.BALANCE, "apple", 70);
        fruitTransactionDao.addToStorage(fruitTransaction);
        FruitTransaction fruitTransactionSupply
                = FruitTransaction.of(Operation.SUPPLY, "apple", 40);
        supplyActivityHandler
                .setAmountOfFruit(fruitTransactionSupply);
        FruitTransaction expected
                = FruitTransaction.of(Operation.BALANCE, "apple", 110);
        FruitTransaction actual = fruitTransactionDao.getFromStorage(fruitTransaction);
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    void afterEach() {
        Storage.fruitTransactions.clear();
    }
}
