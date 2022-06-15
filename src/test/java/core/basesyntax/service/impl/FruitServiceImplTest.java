package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImp;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
    private FruitDao fruitDao;
    private FruitService fruitService;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImp();
        fruitService = new FruitServiceImpl(fruitDao);
    }

    @Test
    public void getAll_emptyStorage_ok() {
        String expected = "";
        String actual = fruitService.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getAll_storageWithParameters_Ok() {
        FruitTransaction banana = new FruitTransaction();
        banana.setFruit("banana");
        banana.setOperation(FruitTransaction.Operation.BALANCE);
        banana.setQuantity(152);
        FruitTransaction apple = new FruitTransaction();
        apple.setFruit("apple");
        apple.setOperation(FruitTransaction.Operation.BALANCE);
        apple.setQuantity(90);
        fruitDao.add(banana);
        fruitDao.add(apple);
        String expected = System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String actual = fruitService.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void add_addWithCorrectParameter_Ok() {
        FruitTransaction actual = new FruitTransaction();
        actual.setFruit("banana");
        actual.setOperation(FruitTransaction.Operation.BALANCE);
        actual.setQuantity(152);
        fruitDao.add(actual);
        FruitTransaction expected = fruitDao.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void add_addNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitService.add(null);
        });
    }

    @Test
    public void add_parameterFruitNull_notOk() {
        FruitTransaction parameterFruitNull = new FruitTransaction();
        parameterFruitNull.setFruit(null);
        parameterFruitNull.setQuantity(10);
        parameterFruitNull.setOperation(FruitTransaction.Operation.BALANCE);
        assertThrows(RuntimeException.class, () -> {
            fruitService.add(parameterFruitNull);
        });
    }

    @Test
    public void add_parameterQuantityNull_notOk() {
        FruitTransaction parameterFruitNull = new FruitTransaction();
        parameterFruitNull.setFruit("banana");
        parameterFruitNull.setQuantity(0);
        parameterFruitNull.setOperation(FruitTransaction.Operation.BALANCE);
        assertThrows(RuntimeException.class, () -> {
            fruitService.add(parameterFruitNull);
        });
    }

    @Test
    public void add_parameterOperationNull_notOk() {
        FruitTransaction parameterFruitNull = new FruitTransaction();
        parameterFruitNull.setFruit("banana");
        parameterFruitNull.setQuantity(100);
        parameterFruitNull.setOperation(null);
        assertThrows(RuntimeException.class, () -> {
            fruitService.add(parameterFruitNull);
        });
    }

    @After
    public void tearDown() throws Exception {
        Storage.warehouse.clear();
    }
}
