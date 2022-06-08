package service;

import dao.ProductAccountDaoImpl;
import db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShopServiceImplTest {
    private static Map<Operation, AmountHandler> amountHandlerMap =
            new HashMap<Operation, AmountHandler>();

    @Before
    public void setUp() {

        amountHandlerMap.put(Operation.BALANCE,new AmountHandlerBalance());
        amountHandlerMap.put(Operation.SUPPLY,new AmountHandlerSupply());
        amountHandlerMap.put(Operation.PURCHASE,new AmountHandlerPurchase());
        amountHandlerMap.put(Operation.RETURN,new AmountHandlerReturn());
    }

    @Test
    public void testExecProductTransaction_OK() {

        OperationHandlerStrategyImpl amountStrategy =
                new OperationHandlerStrategyImpl(amountHandlerMap);
        Storage memdb = new Storage();
        memdb.products.clear();
        ProductAccountDaoImpl dao = new ProductAccountDaoImpl(memdb);
        ShopServiceImpl fruitShop = new ShopServiceImpl(dao, amountStrategy);

        Assert.assertTrue(fruitShop.execProductTransaction("product0",13,"b"));
        Assert.assertEquals("product0",memdb.products.get(0).getName());
        Assert.assertEquals("",Integer.valueOf(13),memdb.products.get(0).getAmount());
        fruitShop.execProductTransaction("product0",13,"p");
        Assert.assertEquals("",Integer.valueOf(0),memdb.products.get(0).getAmount());

    }

    @Test
    public void testExecProductTransaction_False() {
        Map<Operation, AmountHandler> amountHandlerMap = new HashMap<Operation, AmountHandler>();
        amountHandlerMap.put(Operation.BALANCE,new AmountHandlerBalance());
        amountHandlerMap.put(Operation.SUPPLY,new AmountHandlerSupply());
        amountHandlerMap.put(Operation.PURCHASE,new AmountHandlerPurchase());
        amountHandlerMap.put(Operation.RETURN,new AmountHandlerReturn());

        OperationHandlerStrategyImpl amountStrategy =
                new OperationHandlerStrategyImpl(amountHandlerMap);
        Storage memdb = new Storage();
        memdb.products.clear();
        ProductAccountDaoImpl dao = new ProductAccountDaoImpl(memdb);
        ShopServiceImpl fruitShop = new ShopServiceImpl(dao, amountStrategy);

        Assert.assertFalse(fruitShop.execProductTransaction("product0",13,"x"));
    }
}


