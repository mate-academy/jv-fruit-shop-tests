package service;

import dao.ProductAccountDaoImpl;
import db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    @org.junit.jupiter.api.Test
    void execProductTransaction() {
        Assert.assertFalse(false);
    }

    @Test
    void testExecProductTransaction() {
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

        fruitShop.execProductTransaction("product0",13,"b");
        Assert.assertEquals("product0",memdb.products.get(0).getName());
        Assert.assertEquals("",(int)13,(int)memdb.products.get(0).getAmount());
        fruitShop.execProductTransaction("product0",13,"p");
        Assert.assertEquals("",(int)0,(int)memdb.products.get(0).getAmount());

    }
}


