package core.basesyntax.operation.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImp;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private FruitDao fruitDao;
    private OperationHandler operationHandler;
    private FruitService fruitService;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImp();
        fruitService = new FruitServiceImpl(fruitDao);
        operationHandler = new BalanceOperationHandler(fruitService);
    }

    @Test
    public void handle_correctParameters_ok() {
        FruitTransaction actual = new FruitTransaction();
        actual.setFruit("banana");
        actual.setOperation(FruitTransaction.Operation.BALANCE);
        actual.setQuantity(152);
        operationHandler.handle(actual);
        FruitTransaction expected = fruitDao.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void handle_parameterNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null);
        });
    }

    @Test
    public void handle_parameterNotSetFields_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(new FruitTransaction());
        });
    }

    @After
    public void tearDown() throws Exception {
        Storage.warehouse.clear();
    }
}
