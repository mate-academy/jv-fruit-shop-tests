package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private final FruitDao dao = new FruitDaoImpl();
    private FruitTransaction transaction;

    @Before
    public void setUp() {
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(100);
        dao.add(transaction);
    }

    @Test
    public void add_Ok() {
        dao.add(transaction);
        FruitTransaction actual = Storage.transactions.get(0);
        Assert.assertEquals(transaction, actual);
        Storage.transactions.clear();
    }

    @Test
    public void getByOperation_Ok() {
        List<FruitTransaction> expected = List.of(transaction);
        List<FruitTransaction> actual = dao.getByOperation(FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(expected, actual);
        Storage.transactions.clear();
    }

    @Test
    public void get_Ok() {
        List<FruitTransaction> actual = dao.get();
        List<FruitTransaction> expected = List.of(transaction);
        Assert.assertEquals(expected, actual);
        Storage.transactions.clear();
    }

    @Test
    public void getFruitOperationsList_Ok() {
        List<FruitTransaction> expected = List.of(transaction);
        List<FruitTransaction> fruitOperationsList = dao.getFruitOperationsList("b", "banana");
        Assert.assertEquals(expected, fruitOperationsList);
        Storage.transactions.clear();
    }
}
