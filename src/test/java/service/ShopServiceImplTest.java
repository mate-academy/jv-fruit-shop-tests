package service;

import dao.ProductAccountDaoImpl;
import db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ShopServiceImplTest {

    @Test
    public void testExecProductTransaction_OK() {
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

        Assert.assertTrue(fruitShop.execProductTransaction("product0",13,"b"));
        Assert.assertEquals("product0",memdb.products.get(0).getName());
        Assert.assertEquals("",(int)13,(int)memdb.products.get(0).getAmount());
        fruitShop.execProductTransaction("product0",13,"p");
        Assert.assertEquals("",(int)0,(int)memdb.products.get(0).getAmount());

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


