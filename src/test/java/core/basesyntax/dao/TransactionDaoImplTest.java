package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.Main;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.strategy.TransactionStrategy;
import core.basesyntax.service.transactions.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionDaoImplTest {
    private static TransactionStrategy transactionStrategy;
    private static TransactionDao transactionDao;
    private static final Fruit banana = new Fruit("banana");
    private static final Fruit apple = new Fruit("apple");

    @BeforeClass
    public static void beforeClass() {
        transactionStrategy = new TransactionStrategyImpl(Main.createOperationsMap());
        transactionDao = new TransactionDaoImpl(transactionStrategy);
        transactionDao.add(banana, 10);
        transactionDao.add(apple, 20);
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.clear();
        FruitTransaction fruitTransactionBanana = new FruitTransaction();
        fruitTransactionBanana.setFruit("banana");
        fruitTransactionBanana.setQuantity(10);
        fruitTransactionBanana.setOperation(FruitTransaction.Operation.BALANCE);
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(fruitTransactionBanana);

        FruitTransaction fruitTransactionApple = new FruitTransaction();
        fruitTransactionApple.setFruit("apple");
        fruitTransactionApple.setQuantity(20);
        fruitTransactionApple.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactions.add(fruitTransactionApple);
        transactionDao.addAll(fruitTransactions);
    }

    @Test
    public void add_Ok() {
        int expectedStorageSize = 2;
        assertEquals(expectedStorageSize, Storage.fruitStorage.size());
    }

    @Test
    public void getFruitQuantity_Ok() {
        Integer expectedQuantity = 10;
        assertEquals(expectedQuantity, transactionDao.get(banana));
    }

    @Test
    public void addAll_Ok() {
        Integer expectedQuantity = 10;
        int expectedStorageSize = 2;
        assertEquals(expectedStorageSize, Storage.fruitStorage.size());
        assertEquals(expectedQuantity, transactionDao.get(banana));
    }

    @Test
    public void getAll_Ok() {
        StringBuilder expected = new StringBuilder();
        expected.append("banana ").append(10).append(System.lineSeparator())
                .append("apple ").append(20).append(System.lineSeparator());
        String storageInfo = transactionDao.getAll();
        assertEquals(expected.toString(), storageInfo);
    }
}
