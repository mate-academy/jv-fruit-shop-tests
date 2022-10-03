package core.basesyntax.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.*;

import core.basesyntax.storage.Storage;
import org.junit.Before;
import org.junit.Test;

public class OperationTests {
    private OperationHandler operationHandler;
    private FruitTransaction banana = new FruitTransaction();

    @Before
    public void setUp() throws Exception {
        banana.setFruit("banana");
        banana.setQuantity(10);
        Storage.fruitsStorage.clear();
    }

    @Test
    public void balanceHandler_CorrectData_Ok() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandler = new BalanceHandler(fruitsDao);
        operationHandler.handle(banana);
        int expected = 10;
        assertEquals(fruitsDao.getQuantityByFruit(banana.getFruit()), expected);
    }

    @Test
    public void balanceHandler_NullData_NotOk() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandler = new BalanceHandler(fruitsDao);
        assertThrows(RuntimeException.class, () -> operationHandler.handle(null));
    }

    @Test
    public void purchaseHandler_CorrectData_Ok() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        fruitsDao.addFruit("banana", 20);
        operationHandler = new PurchaseHandler(fruitsDao);
        operationHandler.handle(banana);
        int expected = 10;
        assertEquals(fruitsDao.getQuantityByFruit(banana.getFruit()), expected);
    }

    @Test
    public void purchaseHandler_NullData_NotOk() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        fruitsDao.addFruit("banana", 20);
        operationHandler = new PurchaseHandler(fruitsDao);
        assertThrows(RuntimeException.class, () -> operationHandler.handle(null));
    }

    @Test
    public void purchaseHandler_FruitsDaoDontContainEnough_NotOk() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        fruitsDao.addFruit("banana", 20);
        operationHandler = new PurchaseHandler(fruitsDao);
        banana.setQuantity(30);
        assertThrows(RuntimeException.class, () -> operationHandler.handle(banana));
    }

    @Test
    public void returnHandler_CorrectData_Ok() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        fruitsDao.addFruit("banana", 0);
        operationHandler = new ReturnHandler(fruitsDao);
        operationHandler.handle(banana);
        int expected = 10;
        assertEquals(expected,fruitsDao.getQuantityByFruit(banana.getFruit()));
    }

    @Test
    public void returnHandler_CorrectDataFruitDontExist_Ok() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandler = new ReturnHandler(fruitsDao);
        operationHandler.handle(banana);
        int expected = 10;
        assertEquals(expected,fruitsDao.getQuantityByFruit(banana.getFruit()));
    }

    @Test
    public void returnHandler_NullData_NotOk() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandler = new ReturnHandler(fruitsDao);
        assertThrows(RuntimeException.class, () -> operationHandler.handle(null));
    }

    @Test
    public void supplyHandler_CorrectDataButFruitDontExist_Ok() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandler = new SupplyHandler(fruitsDao);
        operationHandler.handle(banana);
        int expected = 10;
        assertEquals(expected, fruitsDao.getQuantityByFruit(banana.getFruit()));
    }

    @Test
    public void supplyHandler_CorrectDataFruitExist_Ok() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandler = new SupplyHandler(fruitsDao);
        fruitsDao.addFruit("banana", 0);
        operationHandler.handle(banana);
        int expected = 10;
        assertEquals(expected, fruitsDao.getQuantityByFruit(banana.getFruit()));
    }

    @Test
    public void supplyHandler_NullData_NotOk() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandler = new SupplyHandler(fruitsDao);
        assertThrows(RuntimeException.class, () -> operationHandler.handle(null));
    }
}
