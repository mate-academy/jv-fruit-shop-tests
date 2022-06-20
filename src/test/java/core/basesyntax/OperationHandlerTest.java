package core.basesyntax;

import static org.junit.Assert.assertEquals;

import dao.FruitDao;
import dao.FruitDaoImpl;
import db.Storage;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.OperationHandler;
import strategy.AddOperationHandler;
import strategy.SetBalanceOperationHandler;
import strategy.SubtractOperationHandler;

public class OperationHandlerTest {
    private static FruitDao fruitDao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> mapOperation = new HashMap<>();
        mapOperation.put(FruitTransaction.Operation.BALANCE,
                new SetBalanceOperationHandler(fruitDao));
        mapOperation.put(FruitTransaction.Operation.PURCHASE,
                new SubtractOperationHandler(fruitDao));
        mapOperation.put(FruitTransaction.Operation.RETURN,
                new AddOperationHandler(fruitDao));
        mapOperation.put(FruitTransaction.Operation.SUPPLY,
                new AddOperationHandler(fruitDao));
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void doTransaction_SupplyOrReturnOperationsWorkRight_ok() {
        String message = "expected quantity and actual are different";
        OperationHandler operationHandler = new AddOperationHandler(fruitDao);
        operationHandler.doTransaction(new FruitTransaction("s", "watermelon", 10));
        operationHandler.doTransaction(new FruitTransaction("r", "watermelon", 5));
        assertEquals(message, 15, fruitDao.get("watermelon").intValue());
    }

    @Test
    public void doTransaction_purchaseOperationQuantityFruitsEnough_ok() {
        String message = "expected quantity and actual are different";
        OperationHandler addOperationHandler = new AddOperationHandler(fruitDao);
        OperationHandler subtractOperationHandler = new SubtractOperationHandler(fruitDao);
        addOperationHandler.doTransaction(new FruitTransaction("s", "banana", 50));
        subtractOperationHandler.doTransaction(new FruitTransaction("p", "banana", 10));
        assertEquals(message, 40, fruitDao.get("banana").intValue());
    }

    @Test
    public void doTransaction_purchaseOperationQuantityFruitsNotEnough_notOk() {
        OperationHandler subtractOperationHandler = new SubtractOperationHandler(fruitDao);
        exception.expect(RuntimeException.class);
        exception.expectMessage("you have no fruit's quantity available for PURCHASE operation");
        subtractOperationHandler.doTransaction(new FruitTransaction("p", "potato", 10));
    }

    @Test
    public void doTransaction_balanceOperationWhenSomeQuantityIsPresent_ok() {
        String message = "something wrong";
        OperationHandler supplyOperationHandler = new AddOperationHandler(fruitDao);
        OperationHandler balanceOperationHandler = new SetBalanceOperationHandler(fruitDao);
        supplyOperationHandler.doTransaction(new FruitTransaction("s", "fungi", 10));
        balanceOperationHandler.doTransaction(new FruitTransaction("s", "fungi", 50));
        assertEquals(message, 50, fruitDao.get("fungi").intValue());
    }
}
