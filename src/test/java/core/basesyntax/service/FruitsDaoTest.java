package core.basesyntax.service;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitsDaoTest {
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_PAPER = "paper";
    private FruitsDao fruitsDao;

    @BeforeEach
    public void setUp() {
        this.fruitsDao = new FruitsDaoImpl();
        fruitsDao.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT_BANANA, 400));
        fruitsDao.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT_APPLE, 100));
    }

    @Test
    public void fruitsDao_getIncorrectValue_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> fruitsDao.get(
                        new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT_PAPER, 50)));
    }

    @Test
    public void fruitsDao_getCorrectValue_Ok() {
        FruitTransaction expectedBanana
                = new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT_BANANA, 400);
        FruitTransaction fruitTransactionBanana = fruitsDao.get(
                new FruitTransaction(FRUIT_BANANA));
        Assertions.assertEquals(expectedBanana, fruitTransactionBanana);

        FruitTransaction expectedApple
                = new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT_APPLE, 100);
        FruitTransaction fruitTransactionApple = fruitsDao.get(
                new FruitTransaction(FRUIT_APPLE));
        Assertions.assertEquals(expectedApple, fruitTransactionApple);
    }

    @Test
    public void fruitsDao_getAllFruits_Ok() {
        List<FruitTransaction> expectedFruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT_BANANA, 400),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT_APPLE, 100));

        List<FruitTransaction> allTransactions = fruitsDao.getAll();
        Assertions.assertEquals(expectedFruitTransactions, allTransactions);
    }

    @Test
    public void fruitsDao_setFruits_Ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.RETURN, FRUIT_BANANA, 200);
        fruitsDao.set(fruitTransaction);
        Assertions.assertEquals(fruitTransaction, fruitsDao.get(fruitTransaction));
    }

    @AfterEach
    public void afterClass() {
        Storage.fruitsStorage.clear();
    }
}
