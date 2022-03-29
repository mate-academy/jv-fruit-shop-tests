package operation;

import dao.FruitShopDao;
import dao.FruitShopDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static ReturnHandler returnHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitShopDao fruitShopDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        returnHandler = new ReturnHandler();
        fruitTransaction = new FruitTransaction();
        fruitShopDao = new FruitShopDaoImpl();
    }

    @Test
    public void validReturnOperation_OK() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(3);
        FruitTransaction secondFruitTransaction = new FruitTransaction();
        secondFruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        secondFruitTransaction.setFruit("banana");
        secondFruitTransaction.setQuantity(3);
        OperationHandler actual = returnHandler.handle(fruitTransaction);
        OperationHandler expected = returnHandler.handle(secondFruitTransaction);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
