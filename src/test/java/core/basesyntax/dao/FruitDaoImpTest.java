package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImpTest {
    private FruitDao fruitDao;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImp();
    }

    @Test
    public void aad_null_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitDao.add(null);
        });
    }

    @Test
    public void add_parameterFruitNull_notOk() {
        FruitTransaction parameterFruitNull = new FruitTransaction();
        parameterFruitNull.setFruit(null);
        parameterFruitNull.setQuantity(10);
        parameterFruitNull.setOperation(FruitTransaction.Operation.BALANCE);
        assertThrows(RuntimeException.class, () -> {
            fruitDao.add(parameterFruitNull);
        });
    }

    @Test
    public void add_parameterQuantityNull_notOk() {
        FruitTransaction parameterFruitNull = new FruitTransaction();
        parameterFruitNull.setFruit("banana");
        parameterFruitNull.setQuantity(0);
        parameterFruitNull.setOperation(FruitTransaction.Operation.BALANCE);
        assertThrows(RuntimeException.class, () -> {
            fruitDao.add(parameterFruitNull);
        });
    }

    @Test
    public void add_parameterOperationNull_notOk() {
        FruitTransaction parameterFruitNull = new FruitTransaction();
        parameterFruitNull.setFruit("banana");
        parameterFruitNull.setQuantity(100);
        parameterFruitNull.setOperation(null);
        assertThrows(RuntimeException.class, () -> {
            fruitDao.add(parameterFruitNull);
        });
    }

    @Test
    public void add_correctParameter_ok() {
        FruitTransaction actual = new FruitTransaction();
        actual.setFruit("banana");
        actual.setQuantity(15);
        actual.setOperation(FruitTransaction.Operation.BALANCE);
        fruitDao.add(actual);
        FruitTransaction expected = Storage.warehouse.get(0);
        assertEquals(expected,actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.warehouse.clear();
    }
}
